package com.dmdev.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuditableEntity is a Querydsl query type for AuditableEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAuditableEntity extends EntityPathBase<AuditableEntity<? extends java.io.Serializable>> {

    private static final long serialVersionUID = 622952028L;

    public static final QAuditableEntity auditableEntity = new QAuditableEntity("auditableEntity");

    public final DateTimePath<java.time.Instant> createdAt = createDateTime("createdAt", java.time.Instant.class);

    public final StringPath createdBy = createString("createdBy");

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAuditableEntity(String variable) {
        super((Class) AuditableEntity.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAuditableEntity(Path<? extends AuditableEntity> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAuditableEntity(PathMetadata metadata) {
        super((Class) AuditableEntity.class, metadata);
    }

}

