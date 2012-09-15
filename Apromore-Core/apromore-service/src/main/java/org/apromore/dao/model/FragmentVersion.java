package org.apromore.dao.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * FragmentVersion generated by hbm2java
 */
@Entity
@Table(name = "fragment_version",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"uri"})
        }
)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Configurable("fragmentVersion")
public class FragmentVersion implements Serializable {

    /**
     * Hard coded for interoperability.
     */
    private static final long serialVersionUID = -9072887404638485548L;

    private Integer id;
    private String uri;
    private String childMappingCode;
    private Integer lockStatus;
    private Integer lockCount;
    private String derivedFromFragment;
    private Integer fragmentSize;
    private String fragmentType;
    private String newestNeighbor;

    private Content content;
    private Cluster cluster;
    private Set<ProcessFragmentMap> processFragmentMaps = new HashSet<ProcessFragmentMap>(0);
    private Set<FragmentVersionDag> fragmentVersionDagsForFragVerId = new HashSet<FragmentVersionDag>(0);
    private Set<FragmentVersionDag> fragmentVersionDagsForChildFragVerId = new HashSet<FragmentVersionDag>(0);
    private Set<ClusterAssignment> clusterAssignments = new HashSet<ClusterAssignment>(0);
    private Set<ProcessModelVersion> processModelVersions = new HashSet<ProcessModelVersion>(0);
    private Set<FragmentDistance> fragmentVersionId1 = new HashSet<FragmentDistance>(0);
    private Set<FragmentDistance> fragmentVersionId2 = new HashSet<FragmentDistance>(0);


    /**
     * Public Constructor.
     */
    public FragmentVersion() { }


    /**
     * returns the Id of this Object.
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    /**
     * Sets the Id of this Object
     * @param id the new Id.
     */
    public void setId(final Integer id) {
        this.id = id;
    }


    /**
     * The URI of this fragmentVersion.
     * @return the uri
     */
    @Column(name = "uri", length = 40)
    public String getUri() {
        return this.uri;
    }

    /**
     * The URI of this fragmentVersion.
     * @param newUri the new uri.
     */
    public void setUri(final String newUri) {
        this.uri = newUri;
    }


    @Column(name = "child_mapping_code", length = 20000)
    public String getChildMappingCode() {
        return this.childMappingCode;
    }

    public void setChildMappingCode(final String newChildMappingCode) {
        this.childMappingCode = newChildMappingCode;
    }


    @Column(name = "lock_status")
    public Integer getLockStatus() {
        return this.lockStatus;
    }

    public void setLockStatus(final Integer newLockStatus) {
        this.lockStatus = newLockStatus;
    }


    @Column(name = "lock_count")
    public Integer getLockCount() {
        return this.lockCount;
    }

    public void setLockCount(final Integer newLockCount) {
        this.lockCount = newLockCount;
    }


    @Column(name = "derived_from_fragment")
    public String getDerivedFromFragment() {
        return this.derivedFromFragment;
    }

    public void setDerivedFromFragment(final String newDerivedFromFragment) {
        this.derivedFromFragment = newDerivedFromFragment;
    }


    @Column(name = "fragment_size")
    public Integer getFragmentSize() {
        return this.fragmentSize;
    }

    public void setFragmentSize(final Integer newFragmentSize) {
        this.fragmentSize = newFragmentSize;
    }


    @Column(name = "fragment_type", length = 10)
    public String getFragmentType() {
        return this.fragmentType;
    }

    public void setFragmentType(final String newFragmentType) {
        this.fragmentType = newFragmentType;
    }


    @Column(name = "newest_neighbor", length = 40)
    public String getNewestNeighbor() {
        return this.newestNeighbor;
    }

    public void setNewestNeighbor(final String newNewestNeighbor) {
        this.newestNeighbor = newNewestNeighbor;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contentId")
    public Content getContent() {
        return this.content;
    }

    public void setContent(final Content newContent) {
        this.content = newContent;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clusterId")
    public Cluster getCluster() {
        return this.cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fragmentVersion")
    public Set<ProcessFragmentMap> getProcessFragmentMaps() {
        return this.processFragmentMaps;
    }

    public void setProcessFragmentMaps(final Set<ProcessFragmentMap> newProcessFragmentMaps) {
        this.processFragmentMaps = newProcessFragmentMaps;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fragmentVersionId")
    public Set<FragmentVersionDag> getFragmentVersionDagsForFragVerId() {
        return this.fragmentVersionDagsForFragVerId;
    }

    public void setFragmentVersionDagsForFragVerId(final Set<FragmentVersionDag> newFragmentVersionDags) {
        this.fragmentVersionDagsForFragVerId = newFragmentVersionDags;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "childFragmentVersionId")
    public Set<FragmentVersionDag> getFragmentVersionDagsForChildFragVerId() {
        return this.fragmentVersionDagsForChildFragVerId;
    }

    public void setFragmentVersionDagsForChildFragVerId(final Set<FragmentVersionDag> newFragmentVersionDags) {
        this.fragmentVersionDagsForChildFragVerId = newFragmentVersionDags;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fragment")
    public Set<ClusterAssignment> getClusterAssignments() {
        return this.clusterAssignments;
    }

    public void setClusterAssignments(Set<ClusterAssignment> newClusterAssignments) {
        this.clusterAssignments = newClusterAssignments;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rootFragmentVersion")
    public Set<ProcessModelVersion> getProcessModelVersions() {
        return this.processModelVersions;
    }

    public void setProcessModelVersions(Set<ProcessModelVersion> processModelVersions) {
        this.processModelVersions = processModelVersions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fragmentVersionId1")
    public Set<FragmentDistance> getFragmentVersionId1() {
        return this.fragmentVersionId1;
    }

    public void setFragmentVersionId1(final Set<FragmentDistance> newFragmentVersionId1) {
        this.fragmentVersionId1 = newFragmentVersionId1;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fragmentVersionId2")
    public Set<FragmentDistance> getFragmentVersionId2() {
        return this.fragmentVersionId2;
    }

    public void setFragmentVersionId2(final Set<FragmentDistance> newFragmentVersionId2) {
        this.fragmentVersionId2 = newFragmentVersionId2;
    }
}


