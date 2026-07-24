package org.midheaven.application.store.tables;

public record QualifiedColumn (String tableAlias, ColumnMetadata metadata){
}
