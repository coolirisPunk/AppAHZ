package com.punkmkt.formula1.databases;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

/**
 * Created by germanpunk on 27/07/15.
 */
@Table(tableName = "Ant",databaseName = AppDatabase.NAME)
public class AntModel extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String type;

    @Column
    boolean isMale;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "queen_id",
                    columnType = Long.class,
                    foreignColumnName = "id")},
            saveForeignKeyModel = false)
    ForeignKeyContainer<QueenModel> queenModelContainer;

    /**
     * Example of setting the model for the queen.
     */
    public void associateQueen(QueenModel queen) {
        queenModelContainer = new ForeignKeyContainer<>(QueenModel.class);
        queenModelContainer.setModel(queen);
    }
}