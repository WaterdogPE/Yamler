import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class HashMap {
    private HashMapConfig hashMapConfig;
    private File file;

    @BeforeSuite
    public void before() {
        hashMapConfig = new HashMapConfig();

        file = new File("temp", "hashMapConfig.yml");
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if(file.exists()) {
            file.delete();
        }
    }

    @Test(priority = 1)
    public void initNull() throws InvalidConfigurationException, IOException {
        hashMapConfig.init(file);

        String fileContents = Util.readFile(file);

        Assert.assertEquals(fileContents.replace("\r", ""), "TestHashMap:\n" +
                "  test:\n" +
                "    test:\n" +
                "      test1: tesw\n");
    }

    @Test(priority = 2)
    public void changeBoolean() throws InvalidConfigurationException, IOException {
        hashMapConfig.TestHashMap.get("test").put("test1", new java.util.HashMap<String, String>() {{
            put("tre", "tew");
        }});

        hashMapConfig.save();

        String fileContents = Util.readFile(file);

        Assert.assertEquals(fileContents.replace("\r", ""), "TestHashMap:\n" +
                "  test:\n" +
                "    test1:\n" +
                "      tre: tew\n" +
                "    test:\n" +
                "      test1: tesw\n");
    }

    @Test(priority = 2)
    public void loadConfig() throws InvalidConfigurationException {
        HashMapConfig hashMapConfig1 = new HashMapConfig();
        hashMapConfig1.load(file);

        Assert.assertEquals(hashMapConfig1.TestHashMap.get("test").size(), 2);
    }
}