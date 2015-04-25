var browser = process.env.BROWSER || 'Chrome';
var watch = process.env.WATCH ? true : false;
var reporter = process.env.REPORTER || 'remote';
var logLevel = process.env.LOG_LEVEL || 'info';
var colors = process.env.COLORS !== undefined ? process.env.COLORS : true;

module.exports = function (config) {
  config.set({

    basePath: '../',

    files: [
      'node_modules/jasmine-core/lib/jasmine-core/jasmine.js',
      'node_modules/karma-jasmine/lib/adapter.js',
      'grails-app/assets/bower_components/jquery/dist/jquery.js',
      'grails-app/assets/bower_components/bootstrap/dist/js/bootstrap.js',
      'grails-app/assets/bower_components/angular/angular.js',
      'grails-app/assets/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
      'grails-app/assets/bower_components/angular-mocks/angular-mocks.js',
      'grails-app/assets/bower_components/angular-resource/angular-resource.js',
      'grails-app/assets/bower_components/angular-route/angular-route.js',
      'grails-app/assets/bower_components/jasmine-data_driven_tests/src/all.js',
      'grails-app/assets/javascripts/application.js',
      'grails-app/assets/javascripts/config/**/*.js',
      'grails-app/assets/javascripts/controllers/**/*.js',
      'grails-app/assets/javascripts/services/**/*.js',
      'test/karma/*.js'
    ],

    logLevel: logLevel,
    colors: colors,
    browsers: [browser],
    reporters: [reporter, 'progress', 'coverage'],
    preprocessors: {
      'grails-app/assets/javascripts/app/**/*.js': ['coverage']
    },

    frameworks: ['jasmine'],
    autoWatch: watch,
    singleRun: !watch,
    remoteReporter: {
      host: 'localhost',
      port: '9877'
    },

    plugins: [
      'karma-jasmine',
      'karma-firefox-launcher',
      'karma-chrome-launcher',
      'karma-phantomjs-launcher',
      'karma-remote-reporter',
      'karma-mocha-reporter',
      'karma-coverage'
    ],

    coverageReporter: {
      // specify a common output directory
      dir: 'target/reports/coverage',
      reporters: [
        // reporters not supporting the `file` property
        { type: 'html', subdir: 'report-html' },
        { type: 'lcov', subdir: 'report-lcov' },
        // reporters supporting the `file` property, use `subdir` to directly
        // output them in the `dir` directory
        { type: 'cobertura', subdir: '.', file: 'cobertura.txt' },
        { type: 'lcovonly', subdir: '.', file: 'report-lcovonly.txt' },
        { type: 'teamcity', subdir: '.', file: 'teamcity.txt' },
        { type: 'text', subdir: '.', file: 'text.txt' },
        { type: 'text-summary', subdir: '.', file: 'text-summary.txt' }
      ]
    }
  });
};