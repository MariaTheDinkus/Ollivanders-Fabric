package com.zundrel.ollivanders.api.woods;

public interface IWood {
    /**
     * Returns the name of the wood.
     *
     * @return The name of the wood.
     */
    String getName();

    /**
     * Returns the translation key of the wood.
     *
     * @return Translation key of the wood.
     */
    String getTranslationKey();

    /**
     * Returns the color of the wood.
     *
     * @return The color of the wood.
     */
    int getColor();

    int getRarity();
}
