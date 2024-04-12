package dominoes.dominoes.ai;

import dominoes.dominoes.ai.impl.RandomBasedArtificialIntelligence;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public enum ArtificialIntelligenceType {

    RANDOM(RandomBasedArtificialIntelligence.class);

    private final Class<RandomBasedArtificialIntelligence> intelligenceClass;

    ArtificialIntelligenceType(Class<RandomBasedArtificialIntelligence> intelligenceClass) {
        this.intelligenceClass = intelligenceClass;
    }

    public ArtificialIntelligence getArtificialIntelligence() {
        try {
            return (ArtificialIntelligence) Arrays.stream(intelligenceClass.getDeclaredConstructors()).findFirst()
                    .orElseThrow()
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
