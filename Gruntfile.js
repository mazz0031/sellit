/*
 * grunt-cli
 * http://gruntjs.com/
 *
 * Copyright (c) 2012 Tyler Kellen, contributors
 * Licensed under the MIT license.
 * https://github.com/gruntjs/grunt-init/blob/master/LICENSE-MIT
 */

'use strict';

module.exports = function (grunt) {
  grunt.loadNpmTasks("grunt-shell");

  if (process.platform == 'win32') {
    grunt.initConfig({
      shell: {
        options: {
          stdout: true
        },
        localInstall: {
          command: "node_modules\\.bin\\bower update --quiet --offline --force"
        },
        webInstall: {
          command: "node_modules\\.bin\\bower update --quiet --force"
        }
      }
    });
  }

  else {
    grunt.initConfig({
      shell: {
        options: {
          stdout: true
        },
        localInstall: {
          command: "node_modules/.bin/bower update --quiet --offline --force"
        },
        webInstall: {
          command: "node_modules/.bin/bower update --quiet --force"
        }
      }
    });
  }

  grunt.registerTask("localInstall", [ "shell:localInstall"]);
  grunt.registerTask("webInstall", [ "shell:webInstall"]);
  grunt.registerTask("default", ["localInstall"]);

};

