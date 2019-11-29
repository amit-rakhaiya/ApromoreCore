package org.apromore.cache.ehcache;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.ByteBufferInputStream;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import de.javakaffee.kryoserializers.*;
import org.deckfour.xes.classification.*;
import org.deckfour.xes.extension.XExtension;
import org.deckfour.xes.extension.XExtensionManager;
import org.deckfour.xes.extension.XExtensionParser;
import org.deckfour.xes.extension.std.*;
import org.deckfour.xes.extension.std.cost.XCostAmount;
import org.deckfour.xes.extension.std.cost.XCostDriver;
import org.deckfour.xes.extension.std.cost.XCostType;
import org.deckfour.xes.factory.XFactoryNaiveImpl;
import org.deckfour.xes.factory.XFactoryRegistry;
import org.deckfour.xes.id.XID;
import org.deckfour.xes.id.XIDFactory;
import org.deckfour.xes.info.XGlobalAttributeNameMap;
import org.deckfour.xes.info.XLogInfoFactory;
import org.deckfour.xes.info.impl.XAttributeInfoImpl;
import org.deckfour.xes.info.impl.XAttributeNameMapImpl;
import org.deckfour.xes.info.impl.XLogInfoImpl;
import org.deckfour.xes.info.impl.XTimeBoundsImpl;
import org.deckfour.xes.model.*;
import org.deckfour.xes.model.buffered.XAttributeMapBufferedImpl;
import org.deckfour.xes.model.impl.*;
import org.deckfour.xes.util.XAttributeUtils;
import org.deckfour.xes.xstream.XExtensionConverter;
import org.ehcache.spi.serialization.Serializer;
import org.ehcache.spi.serialization.SerializerException;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

/**
 *
 */
// tag::thirdPartyTransientSerializer[]
public class TransientXLogKryoSerializer implements Serializer<XLog>, Closeable{

    protected static final Kryo kryo = new Kryo();

    protected Map<Class, Integer> objectHeaderMap = new HashMap<Class, Integer>();  // <1>

    public TransientXLogKryoSerializer() {
    }

    public TransientXLogKryoSerializer(ClassLoader loader) {

//        Reference setting here doesn't work
        kryo.setReferences(false);

//        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
//
//        kryo.register( Collections.EMPTY_LIST.getClass(), new CollectionsEmptyListSerializer() );

//        populateObjectHeadersMap(kryo.register(XLog.class));  // <2>


//        populateObjectHeadersMap(kryo.register(XLogImpl.class));  // <2>
//        populateObjectHeadersMap(kryo.register(XTraceImpl.class));  // <3>
//        populateObjectHeadersMap(kryo.register(XEventImpl.class)); // <4>
//        populateObjectHeadersMap(kryo.register(XAttributeBooleanImpl.class)); // <4>
//        populateObjectHeadersMap(kryo.register(XAttributeCollectionImpl.class)); // <4>
//        populateObjectHeadersMap(kryo.register(XAttributeContainerImpl.class)); // <4>
//        populateObjectHeadersMap(kryo.register(XAttributeContinuousImpl.class)); // <4>
//        populateObjectHeadersMap(kryo.register(XAttributeDiscreteImpl.class)); // <4>
//        populateObjectHeadersMap(kryo.register(XAttributeIDImpl.class)); // <4>
//        populateObjectHeadersMap(kryo.register(XAttributeListImpl.class)); // <4>
//        populateObjectHeadersMap(kryo.register(XAttributeLiteralImpl.class)); // <4>
//        populateObjectHeadersMap(kryo.register(XAttributeMapImpl.class)); // <4>
//        populateObjectHeadersMap(kryo.register(XAttributeTimestampImpl.class)); // <4>
//        populateObjectHeadersMap(kryo.register(XAttributeImpl.class)); // <4>
//        populateObjectHeadersMap(kryo.register(XAttributeMapLazyImpl.class)); // <4>
//        populateObjectHeadersMap(kryo.register(XsDateTimeFormat.class)); // <4>
//        populateObjectHeadersMap(kryo.register(XExtension.class)); // <4>
//        populateObjectHeadersMap(kryo.register(XLifecycleExtension.class)); // <4>
//        populateObjectHeadersMap(kryo.register(org.eclipse.collections.impl.set.mutable.UnifiedSet.class)); // <4>
    }

    protected void populateObjectHeadersMap(Registration reg) {
        objectHeaderMap.put(reg.getType(), reg.getId());  // <5>
    }

    @Override
    public ByteBuffer serialize(XLog object) throws SerializerException {
//        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));

//        kryo.setReferences(false);
//        kryo.setRegistrationRequired(false);

//        kryo.register(XLogImpl.class);
//        kryo.register(XTraceImpl.class);
//        kryo.register(XEventImpl.class);
//        kryo.register(XAttributeBooleanImpl.class);
//        kryo.register(XAttributeCollectionImpl.class);
//        kryo.register(XAttributeContainerImpl.class);
//        kryo.register(XAttributeContinuousImpl.class);
//        kryo.register(XAttributeDiscreteImpl.class);
//        kryo.register(XAttributeIDImpl.class);
//        kryo.register(XAttributeListImpl.class);
//        kryo.register(XAttributeLiteralImpl.class);
//        kryo.register(XAttributeMapImpl.class);
//        kryo.register(XAttributeTimestampImpl.class);
//        kryo.register(XAttributeImpl.class);
//        kryo.register(XAttributeMapLazyImpl.class);
//        kryo.register(XsDateTimeFormat.class);
//
//        kryo.register(XExtension.class);
//        kryo.register(XLifecycleExtension.class);
//        kryo.register(org.eclipse.collections.impl.set.mutable.UnifiedSet.class);

//        populateObjectHeadersMap(kryo.register(XAttributeMapLazyImpl.class, new XAttributeMapSerializer())); // <4>
//        populateObjectHeadersMap(kryo.register(XAttributeMapImpl.class, new XAttributeMapSerializer())); // <4>
        populateObjectHeadersMap(kryo.register(XAttributeMap.class, new XAttributeMapSerializer())); // <4>
//        populateObjectHeadersMap(kryo.register(XAttributeMap.class));

        populateObjectHeadersMap(kryo.register(XLogImpl.class));  // <2>
        populateObjectHeadersMap(kryo.register(XTraceImpl.class));  // <3>
        populateObjectHeadersMap(kryo.register(XEventImpl.class)); // <4>
        populateObjectHeadersMap(kryo.register(XAttributeBooleanImpl.class)); // <4>
        populateObjectHeadersMap(kryo.register(XAttributeCollectionImpl.class)); // <4>
        populateObjectHeadersMap(kryo.register(XAttributeContainerImpl.class)); // <4>
        populateObjectHeadersMap(kryo.register(XAttributeContinuousImpl.class)); // <4>
        populateObjectHeadersMap(kryo.register(XAttributeDiscreteImpl.class)); // <4>
        populateObjectHeadersMap(kryo.register(XAttributeIDImpl.class)); // <4>
        populateObjectHeadersMap(kryo.register(XAttributeListImpl.class)); // <4>
        populateObjectHeadersMap(kryo.register(XAttributeLiteralImpl.class)); // <4>
        populateObjectHeadersMap(kryo.register(XAttributeTimestampImpl.class)); // <4>
        populateObjectHeadersMap(kryo.register(XAttributeImpl.class)); // <4>
        populateObjectHeadersMap(kryo.register(XsDateTimeFormat.class)); // <4>
        populateObjectHeadersMap(kryo.register(XExtension.class)); // <4>
        populateObjectHeadersMap(kryo.register(XLifecycleExtension.class)); // <4>
        populateObjectHeadersMap(kryo.register(org.eclipse.collections.impl.set.mutable.UnifiedSet.class)); // <4>

        populateObjectHeadersMap(kryo.register(XAttribute.class)); // <4>
        populateObjectHeadersMap(kryo.register(XAttributeTimestamp.class));
        populateObjectHeadersMap(kryo.register(XAttributeLiteral.class));
        populateObjectHeadersMap(kryo.register(XAttributeList.class));
        populateObjectHeadersMap(kryo.register(XAttributeID.class));
        populateObjectHeadersMap(kryo.register(XAttributeDiscrete.class));
        populateObjectHeadersMap(kryo.register(XAttributable.class));

        populateObjectHeadersMap(kryo.register(XAttributeMapBufferedImpl.class));
        populateObjectHeadersMap(kryo.register(XID.class));
        populateObjectHeadersMap(kryo.register(XIDFactory.class));
        populateObjectHeadersMap(kryo.register(Objects.class));
        populateObjectHeadersMap(kryo.register(XVisitor.class));
        populateObjectHeadersMap(kryo.register(XAttributeUtils.class));
        populateObjectHeadersMap(kryo.register(XsDateTimeFormat.class));
        populateObjectHeadersMap(kryo.register(XAttributeNameMapImpl.class));

        // info
        populateObjectHeadersMap(kryo.register(XAttributeInfoImpl.class));
        populateObjectHeadersMap(kryo.register(XLogInfoImpl.class));
        populateObjectHeadersMap(kryo.register(XGlobalAttributeNameMap.class));
        populateObjectHeadersMap(kryo.register(XLogInfoFactory.class));
        populateObjectHeadersMap(kryo.register(XFactoryNaiveImpl.class));
        populateObjectHeadersMap(kryo.register(XFactoryRegistry.class));
        populateObjectHeadersMap(kryo.register(XTimeBoundsImpl.class));

        // extension
        populateObjectHeadersMap(kryo.register(XExtension.class));
        populateObjectHeadersMap(kryo.register(XExtendedEvent.class));
        populateObjectHeadersMap(kryo.register(XExtensionManager.class));
        populateObjectHeadersMap(kryo.register(XExtensionParser.class));
        populateObjectHeadersMap(kryo.register(XExtensionConverter.class));
        populateObjectHeadersMap(kryo.register(XAbstractNestedAttributeSupport.class));
        populateObjectHeadersMap(kryo.register(XArtifactLifecycleExtension.class));
        populateObjectHeadersMap(kryo.register(XConceptExtension.class));
        populateObjectHeadersMap(kryo.register(XCostExtension.class));
        populateObjectHeadersMap(kryo.register(XExtendedEvent.class));
        populateObjectHeadersMap(kryo.register(XIdentityExtension.class));
        populateObjectHeadersMap(kryo.register(XLifecycleExtension.class));
        populateObjectHeadersMap(kryo.register(XMicroExtension.class));
        populateObjectHeadersMap(kryo.register(XOrganizationalExtension.class));
        populateObjectHeadersMap(kryo.register(XSemanticExtension.class));
        populateObjectHeadersMap(kryo.register(XSoftwareCommunicationExtension.class));
        populateObjectHeadersMap(kryo.register(XSoftwareEventExtension.class));
        populateObjectHeadersMap(kryo.register(XSoftwareTelemetryExtension.class));
        populateObjectHeadersMap(kryo.register(XTimeExtension.class));
        populateObjectHeadersMap(kryo.register(XCostAmount.class));
        populateObjectHeadersMap(kryo.register(XCostDriver.class));
        populateObjectHeadersMap(kryo.register(XCostType.class));

        populateObjectHeadersMap(kryo.register(XEventAndClassifier.class));
        populateObjectHeadersMap(kryo.register(XEventAttributeClassifier.class));
        populateObjectHeadersMap(kryo.register(XEventClass.class));
        populateObjectHeadersMap(kryo.register(XEventClasses.class));
        populateObjectHeadersMap(kryo.register(XEventLifeTransClassifier.class));
        populateObjectHeadersMap(kryo.register(XEventNameClassifier.class));
        populateObjectHeadersMap(kryo.register(XEventResourceClassifier.class));






//        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));

        // Not needed
//        kryo.register( Arrays.asList( "" ).getClass(), new ArraysAsListSerializer() );
//        kryo.register( Collections.EMPTY_LIST.getClass(), new CollectionsEmptyListSerializer() );
//        kryo.register( Collections.EMPTY_MAP.getClass(), new CollectionsEmptyMapSerializer() );
//        kryo.register( Collections.EMPTY_SET.getClass(), new CollectionsEmptySetSerializer() );
//        kryo.register( Collections.singletonList( "" ).getClass(), new CollectionsSingletonListSerializer() );
//        kryo.register( Collections.singleton( "" ).getClass(), new CollectionsSingletonSetSerializer() );
//        kryo.register( Collections.singletonMap( "", "" ).getClass(), new CollectionsSingletonMapSerializer() );
//        UnmodifiableCollectionsSerializer.registerSerializers( kryo );
//        SynchronizedCollectionsSerializer.registerSerializers( kryo );

        Output output = new Output(new ByteArrayOutputStream());
        kryo.writeObject(output, object);
        output.close();

        return ByteBuffer.wrap(output.getBuffer());
    }

    @Override
    public XLog read(final ByteBuffer binary) throws ClassNotFoundException, SerializerException {

//        kryo.setReferences(false);

//        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));

//        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));

//        ((Kryo.DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());

//        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());

        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));

        // Not needed
//        kryo.register( Arrays.asList( "" ).getClass(), new ArraysAsListSerializer() );
//        kryo.register( Collections.EMPTY_LIST.getClass(), new CollectionsEmptyListSerializer() );
//        kryo.register( Collections.EMPTY_MAP.getClass(), new CollectionsEmptyMapSerializer() );
//        kryo.register( Collections.EMPTY_SET.getClass(), new CollectionsEmptySetSerializer() );
//        kryo.register( Collections.singletonList( "" ).getClass(), new CollectionsSingletonListSerializer() );
//        kryo.register( Collections.singleton( "" ).getClass(), new CollectionsSingletonSetSerializer() );
//        kryo.register( Collections.singletonMap( "", "" ).getClass(), new CollectionsSingletonMapSerializer() );
//        UnmodifiableCollectionsSerializer.registerSerializers( kryo );
//        SynchronizedCollectionsSerializer.registerSerializers( kryo );

//        kryo.setReferences(false);

        Input input =  new Input(new ByteBufferInputStream(binary)) ;
        return kryo.readObject(input, XLogImpl.class);
    }

    @Override
    public boolean equals(final XLog object, final ByteBuffer binary) throws ClassNotFoundException, SerializerException {
        return object.equals(read(binary));
    }

    @Override
    public void close() throws IOException {
        objectHeaderMap.clear();
    }

}
// end::thirdPartyTransientSerializer[]