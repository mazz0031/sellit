import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.firefox.FirefoxDriver

driver = {
    //new PhantomJSDriver(new DesiredCapabilities())
    new ChromeDriver()
    //new FirefoxDriver()
}