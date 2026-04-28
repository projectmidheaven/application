package org.midheaven.application.store.tables;

public record SortConstraint(ColumnMetadata column, SortOrder sortOrder) implements ColumnConstraint {

}
