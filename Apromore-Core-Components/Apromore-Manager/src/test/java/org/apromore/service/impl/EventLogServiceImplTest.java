package org.apromore.service.impl;

import org.apromore.dao.FolderRepository;
import org.apromore.dao.GroupRepository;
import org.apromore.dao.LogRepository;
import org.apromore.dao.StatisticRepository;
import org.apromore.dao.model.Log;
import org.apromore.dao.model.Statistic;
import org.apromore.service.UserService;
import org.apromore.service.helper.UserInterfaceHelper;
import org.apromore.util.StatType;
import org.apromore.util.UuidAdapter;
import org.deckfour.xes.model.XAttribute;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.impl.XAttributeLiteralImpl;
import org.deckfour.xes.model.impl.XAttributeMapImpl;
import org.deckfour.xes.model.impl.XLogImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.*;

import static org.apromore.service.impl.EventLogServiceImpl.PARENT_NODE_FLAG;
import static org.apromore.service.impl.EventLogServiceImpl.STAT_NODE_NAME;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.powermock.api.easymock.PowerMock.*;

public class EventLogServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogServiceImplTest.class);

    // inject EntityManager for simple test
    private static EntityManagerFactory emf = null;
    public EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("TESTApromore");
        }
        return emf;
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private LogRepository logRepository;
    private GroupRepository groupRepository;
    private FolderRepository folderRepo;
    private UserService userSrv;
    private UserInterfaceHelper ui;
    private StatisticRepository statisticRepository;

    private EventLogServiceImpl eventLogService;

    @Before
    public final void setUp() throws Exception {
        logRepository = createMock(LogRepository.class);
        groupRepository = createMock(GroupRepository.class);
        folderRepo = createMock(FolderRepository.class);
        userSrv = createMock(UserService.class);
        ui = createMock(UserInterfaceHelper.class);
        statisticRepository = createMock(StatisticRepository.class);

        eventLogService = new EventLogServiceImpl(logRepository, groupRepository, folderRepo, userSrv, ui, statisticRepository);
    }


    @Test
    public void getStatsTest() {
        List<Statistic> stats = new ArrayList<>();
        Integer logId = 001;
        expect(statisticRepository.findByLogid(logId)).andReturn(stats);
        replay(statisticRepository);

        List<Statistic> result = eventLogService.getStats(logId);
        verify(statisticRepository);
        assertThat(result, equalTo(stats));
    }

    @Test
    public void getXLogWithStatsTest() {



        List<Statistic> stats = new ArrayList<>();

        Statistic parent = new Statistic();
        parent.setId("parent".getBytes());
        parent.setStat_key("key");
        parent.setLogid(88);
        parent.setPid("0".getBytes());
        parent.setStat_value("01");
        stats.add(parent);

        Statistic child = new Statistic();
        child.setId("child".getBytes());
        child.setStat_key("child_key");
        child.setLogid(88);
        child.setPid("parent".getBytes());
        child.setStat_value("02");
        stats.add(child);

        Integer logId = 001;

        expect(statisticRepository.findByLogid(logId)).andReturn(stats);
        replay(statisticRepository);

//        List<Statistic> result = eventLogService.getStats(logId);
//        verify(statisticRepository);
//        assertThat(result, equalTo(stats));

        Log log = new Log();
        XLog xlog = new XLogImpl(new XAttributeMapImpl());

        expect(logRepository.findUniqueByID(logId)).andReturn(log);
        expect(logRepository.getProcessLog(log)).andReturn(xlog);
        replay(logRepository);
//        XLog expectXlog = eventLogService.getXLog(logId);
//        verify(logRepository);
//        assertThat(expectXlog, equalTo(xlog));

        XLog expectResult = eventLogService.getXLogWithStats(logId);
        verify(statisticRepository, logRepository);

        XAttribute statsAttribute = expectResult.getAttributes().get(STAT_NODE_NAME);

        assertThat(statsAttribute, equalTo(new XAttributeLiteralImpl(STAT_NODE_NAME, "")));
        assertThat(statsAttribute.getAttributes().size(), equalTo(1));
        assertThat(statsAttribute.getAttributes().get("key"), equalTo(new XAttributeLiteralImpl("key", "01")));
        assertThat(statsAttribute.getAttributes().get("key").getAttributes().get("child_key"), equalTo(new XAttributeLiteralImpl("child_key", "02")));
    }


    @Test
    public void flattenNestedMapTest() {

        Map<String, Map<String, Integer>> options = new HashMap<>();

        Map<String, Integer> options_frequency = new HashMap<>();

        options_frequency.put("Activity", 10);
        options_frequency.put("direct:follow", 40);
        options.put("concept:name", options_frequency);
        options.put("concept:test", options_frequency);

        List<Statistic> result = eventLogService.flattenNestedMap(options, 88);

        assertThat(result.size(), equalTo(6));
        assertThat(result.get(0).getPid(), equalTo("0".getBytes()));
        assertThat(result.get(0).getStat_key(), equalTo("concept:name"));
        assertThat(result.get(1).getStat_key(), equalTo("direct:follow"));
        assertThat(result.get(1).getStat_value(), equalTo("40"));
        assertThat(result.get(2).getStat_key(), equalTo("Activity"));
        assertThat(result.get(2).getStat_value(), equalTo("10"));
        assertThat(result.get(3).getStat_key(), equalTo("concept:test"));
        assertThat(result.get(4).getStat_key(), equalTo("direct:follow"));
        assertThat(result.get(4).getStat_value(), equalTo("40"));
        assertThat(result.get(5).getStat_key(), equalTo("Activity"));
        assertThat(result.get(5).getStat_value(), equalTo("10"));
    }

    @Test
    public void flattenNestedStringMapTest() {

        Map<String, Map<String, String>> options = new HashMap<>();

        Map<String, String> options_frequency = new HashMap<>();

        options_frequency.put("Activity", "10");
        options_frequency.put("direct:follow", "40");
        options.put("concept:name", options_frequency);
        options.put("concept:test", options_frequency);

        List<Statistic> result = eventLogService.flattenNestedStringMap(options, 88, StatType.FILTER);

        assertThat(result.size(), equalTo(6));
        assertThat(result.get(0).getPid(), equalTo("0".getBytes()));
        assertThat(result.get(0).getStat_key(), equalTo(StatType.FILTER.toString()));
        assertThat(result.get(0).getStat_value(), equalTo("concept:name"));
        assertThat(result.get(0).getLogid(), equalTo(88));

        assertThat(result.get(1).getStat_key(), equalTo("direct:follow"));
        assertThat(result.get(1).getStat_value(), equalTo("40"));
        assertThat(result.get(1).getPid(), equalTo(result.get(0).getId()));
        assertThat(result.get(1).getLogid(), equalTo(88));

        assertThat(result.get(2).getStat_key(), equalTo("Activity"));
        assertThat(result.get(2).getStat_value(), equalTo("10"));

        assertThat(result.get(3).getStat_key(), equalTo(StatType.FILTER.toString()));
        assertThat(result.get(3).getStat_value(), equalTo("concept:test"));

        assertThat(result.get(4).getStat_key(), equalTo("direct:follow"));
        assertThat(result.get(4).getStat_value(), equalTo("40"));

        assertThat(result.get(5).getStat_key(), equalTo("Activity"));
        assertThat(result.get(5).getStat_value(), equalTo("10"));
    }


    @Test
    @Rollback
    public void simpleTest() {


        EntityManager em = getEntityManagerFactory().createEntityManager();
        assert em != null;
        Statistic fe = new Statistic();
        fe.setId(UuidAdapter.getBytesFromUUID(UUID.randomUUID()));
        fe.setStat_key("key");
        fe.setLogid(88);
        fe.setPid(UuidAdapter.getBytesFromUUID(UUID.randomUUID()));
        fe.setStat_value("value");
        em.getTransaction().begin();


        em.persist(fe);
//        Query query = em.createQuery("SELECT s FROM Statistic s WHERE s.logid =:param").setParameter("param", fe.getLogid());
        Query query = em.createQuery("SELECT s FROM Statistic s WHERE s.logid =:param1 AND s.stat_value=:param2")
                .setParameter("param1", 88)
                .setParameter("param2", "value");
        List<Statistic> stats = query.getResultList();

        for (Statistic stat : stats) {
            LOGGER.info(stat.getStat_value());
        }

        em.flush();
        em.getTransaction().commit();
        em.close();

//        logRepositoryCustom.saveStat(stats.get(0));
//        eventLogService.insertStatistic(stat);
    }

    @Test
    @Rollback
    public void batchInsertTest() {

        // *******  profiling code start here ********
        long startTime = System.nanoTime();
        // *******  profiling code end here ********

        EntityManager em = getEntityManagerFactory().createEntityManager();
        assert em != null;
        em.getTransaction().begin();

        for (int i = 0; i < 100000; i++) {

            Statistic fe = new Statistic();
            fe.setId(UuidAdapter.getBytesFromUUID(UUID.randomUUID()));
            fe.setStat_key("key");
            fe.setLogid(88);
            fe.setPid(UuidAdapter.getBytesFromUUID(UUID.randomUUID()));
            fe.setStat_value(Double.toString(Math.random()));

            em.persist(fe);
            if ((i % 10000) == 0) {
                em.getTransaction().commit();
                em.clear();
                em.getTransaction().begin();
            }
        }
        em.getTransaction().commit();
//        em.close();

        // *******  profiling code start here ********
        long elapsedNanos = System.nanoTime() - startTime;
        LOGGER.info("Elapsed time: " + elapsedNanos / 1000000 + " ms");
        LOGGER.info("Insert speed: " + 100000 / ( elapsedNanos / 1000000 /1000 ) + " records/sec");
        // *******  profiling code end here ********

    }

//    @Test
//    public void getStatsByType() {
//
//        List<Statistic> stats = new ArrayList<>();
//        List<Dashboard> dashboard = new ArrayList<>();
//        Integer logId = 001;
//        expect(statisticRepository.findByLogid(logId)).andReturn(stats);
//        replay(statisticRepository);
//
//        expect(dashboardRepository.findByLogid(logId)).andReturn(dashboard);
//        replay(dashboardRepository);
//
//        List<Statistic> result = (List<Statistic>) eventLogService.getStatsByType(logId, StatType.FILTER);
//        verify(statisticRepository);
//        assertThat(result, equalTo(stats));
//
//        List<Dashboard> dbList = (List<Dashboard>) eventLogService.getStatsByType(logId, StatType.ACTIVITY);
//        verify(statisticRepository);
//        assertThat(dbList, equalTo(dashboard));
//    }
}