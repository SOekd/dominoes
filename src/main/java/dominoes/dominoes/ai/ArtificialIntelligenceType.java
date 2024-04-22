package dominoes.dominoes.ai;

import dominoes.dominoes.ai.impl.RandomBasedArtificialIntelligence;
import dominoes.dominoes.ai.impl.ProbabilityBasedArtificialIntelligence;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public enum ArtificialIntelligenceType {

    RANDOM(RandomBasedArtificialIntelligence.class),
    SEARCH(ProbabilityBasedArtificialIntelligence.class);

    private final Class<? extends ArtificialIntelligence> intelligenceClass;

    ArtificialIntelligenceType(Class<? extends ArtificialIntelligence> intelligenceClass) {
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
