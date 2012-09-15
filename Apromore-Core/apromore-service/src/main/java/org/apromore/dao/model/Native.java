package org.apromore.dao.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Configurable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Stores the native something in apromore.
 *
 * @author <a href="mailto:cam.james@gmail.com">Cameron James</a>
 */
@Entity
@Table(name = "native",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"processModelVersionId", "nat_type"})
        }
)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Configurable("native")
public class Native implements Serializable {

    /**
     * Hard coded for interoperability.
     */
    private static final long serialVersionUID = -235332908738485548L;

    private Integer id;
    private String content;

    private NativeType nativeType;
    private ProcessModelVersion processModelVersion;
    private Set<Annotation> annotations = new HashSet<Annotation>(0);

    /**
     * Default Constructor.
     */
    public Native() { }


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
     * Get the content for the Object.
     * @return Returns the content.
     */
    @Column(name = "content")
    public String getContent() {
        return this.content;
    }

    /**
     * Set the content for the Object.
     *
     * @param newContent The content to set.
     */
    public void setContent(final String newContent) {
        this.content = newContent;
    }

    /**
     * Get the nativeType for the Object.
     *
     * @return Returns the nativeType.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nat_type")
    public NativeType getNativeType() {
        return this.nativeType;
    }

    /**
     * Set the nativeType for the Object.
     *
     * @param newNativeType The nativeType to set.
     */
    public void setNativeType(final NativeType newNativeType) {
        this.nativeType = newNativeType;
    }

    /**
     * Get the process Model Version for the Object.
     *
     * @return Returns the process Model Version.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processModelVersionId")
    public ProcessModelVersion getProcessModelVersion() {
        return this.processModelVersion;
    }

    /**
     * Set the process Model Version for the Object.
     *
     * @param newProcessModelVersion The process Model Version format to set.
     */
    public void setProcessModelVersion(ProcessModelVersion newProcessModelVersion) {
        this.processModelVersion = newProcessModelVersion;
    }

    /**
     * Get the annotations for the Object.
     *
     * @return Returns the annotations.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "natve")
    public Set<Annotation> getAnnotations() {
        return this.annotations;
    }

    /**
     * Set the annotations for the Object.
     *
     * @param newAnnotations The annotations to set.
     */
    public void setAnnotations(final Set<Annotation> newAnnotations) {
        this.annotations = newAnnotations;
    }

}
