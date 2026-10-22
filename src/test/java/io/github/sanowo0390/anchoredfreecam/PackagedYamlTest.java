package io.github.sanowo0390.anchoredfreecam;

import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PackagedYamlTest {
    private static Reader open(String name) {
        return new InputStreamReader(
                PackagedYamlTest.class.getClassLoader().getResourceAsStream(name), StandardCharsets.UTF_8);
    }

    private static void assertNoDuplicateKeys(String name) throws Exception {
        LoaderOptions options = new LoaderOptions();
        options.setAllowDuplicateKeys(false);
        try (Reader reader = open(name)) {
            assertNotNull(new Yaml(options).load(reader));
        }
    }

    @Test
    void pluginDescriptorHasNoDuplicateKeys() throws Exception {
        assertNoDuplicateKeys("plugin.yml");
    }

    @Test
    void defaultConfigHasNoDuplicateKeys() throws Exception {
        assertNoDuplicateKeys("config.yml");
    }

    @Test
    void languageFilesHaveNoDuplicateKeys() throws Exception {
        assertNoDuplicateKeys("lang/ja.yml");
        assertNoDuplicateKeys("lang/en.yml");
    }

    @Test
    void softdependKeepsEveryOptionalIntegration() throws Exception {
        try (Reader reader = open("plugin.yml")) {
            List<String> softdepend = YamlConfiguration.loadConfiguration(reader).getStringList("softdepend");
            assertTrue(softdepend.contains("LuckPerms"));
            assertTrue(softdepend.contains("MyPet"));
            assertTrue(softdepend.contains("Towny"));
        }
    }
}
