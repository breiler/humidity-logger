module.exports = function(grunt) {
  var proxySnippet = require('grunt-connect-proxy/lib/utils').proxyRequest;

  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    copy: {
      build: {
        cwd: 'src/main/resources/public',
        src: [ '**' ],
        dest: 'target',
        expand: true
      },
      build_vendor_assets: {
        files: [
          {
            src: ['bower_components/bootstrap/dist/css/bootstrap.min.css', '!**/*.md'],
            dest: 'target/css',
            cwd: '.',
            expand: true,
            flatten: true
          }
      ]
    },
    },
    clean: {
      build: {
        src: [ 'target' ]
      },
    },
    uglify: {
      core: {
        files: [{
          expand: true,
          cwd: 'target',
          src: ['js/**/*.js', 'libs/**/*.js'],
          dest: "target",
          ext: '.min.js'
        }]
      }
    },
    bower: {
      install: {
        options: {
          install: true,
          copy: true,
          targetDir: 'target/libs',
          cleanTargetDir: true,
          expand: true
        }
      }
    },
    connect: {
    		options: {
    			port: 9002,
    			// Change this to '0.0.0.0' to access the server from outside.
    			hostname: '0.0.0.0',
    			livereload: 35729
    		},

		proxies: [
			{
				context: '/devices',
				host: 'localhost',
				port: 8080,
				https: false,
				changeOrigin: true,
				xforward: false
			}
		],
    livereload: {
      options: {
        open: true,
        base: [
          '.tmp',
          'target'
        ],
        middleware: function (connect, options) {
          var middlewares = [];

          if (!Array.isArray(options.base)) {
            options.base = [options.base];
          }

          // Setup the proxy
          middlewares.push(require('grunt-connect-proxy/lib/utils').proxyRequest);

          // Serve static files
          options.base.forEach(function(base) {
            middlewares.push(connect.static(base));
          });

          return middlewares;
        }
      }
      }
	},

	watch: {
      copy: {
        files: [ 'src/main/resources/public/**' ],
        tasks: [ 'copy', 'uglify' ]
      }
    }
  });

  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-bower-task');
  grunt.loadNpmTasks('grunt-contrib-connect');
  grunt.loadNpmTasks('grunt-connect-proxy');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-copy');
  grunt.loadNpmTasks('grunt-contrib-clean');
  grunt.loadNpmTasks('grunt-contrib-less');

  grunt.registerTask('serve', ['default', 'configureProxies:server', 'connect:livereload', 'watch']);
  grunt.registerTask('default', ['clean', 'bower', 'copy', 'uglify']);
};