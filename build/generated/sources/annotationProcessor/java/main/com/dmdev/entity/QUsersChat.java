package com.dmdev.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsersChat is a Querydsl query type for UsersChat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersChat extends EntityPathBase<UsersChat> {

    private static final long serialVersionUID = 1638869732L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsersChat usersChat = new QUsersChat("usersChat");

    public final QAuditableEntity _super = new QAuditableEntity(this);

    public final QChat chat;

    //inherited
    public final DateTimePath<java.time.Instant> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser user;

    public QUsersChat(String variable) {
        this(UsersChat.class, forVariable(variable), INITS);
    }

    public QUsersChat(Path<? extends UsersChat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUsersChat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUsersChat(PathMetadata metadata, PathInits inits) {
        this(UsersChat.class, metadata, inits);
    }

    public QUsersChat(Class<? extends UsersChat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chat = inits.isInitialized("chat") ? new QChat(forProperty("chat")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

