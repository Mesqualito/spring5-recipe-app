package com.eigenbaumarkt.spring5recipeapp.converters;

import com.eigenbaumarkt.spring5recipeapp.commands.UnitOfMeasureCommand;
import com.eigenbaumarkt.spring5recipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    // Spring ist nicht thread-sicher, deswegen hier der Einsatz von Lomboks '@Synchronized',
    // um die Anwendung in einem multi-threaded environment verwenden zu können
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if (source == null) {
            return null;
        }

        // 'final' für Code-Sicherheit: immutable uom
        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(source.getId());
        uom.setDescription(source.getDescription());
        return uom;
    }
}
