package sellit

/**
 * Created by mark.mazzitello on 5/8/2015.
 */

import de.is24.util.karmatestrunner.junit.KarmaTestSuiteRunner
import org.junit.runner.RunWith

@RunWith(KarmaTestSuiteRunner)
@KarmaTestSuiteRunner.KarmaProcessName('node_modules/karma/bin/karma')
@KarmaTestSuiteRunner.KarmaConfigPath('./test/karma.conf.js')
@KarmaTestSuiteRunner.KarmaRemoteServerPort(9877)
class JavaScriptUnitTestKarmaSuite {

}
