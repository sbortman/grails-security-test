# Steps to create secure grails application

1.  Create a new grails application:
    ```
    grails create-app grails-security-test
    ```
2.  Add the Spring Security Core plugin dependency to build.gradle:
    ```
    implementation( 'org.grails.plugins:spring-security-core:6.1.1' )
    ```
3.  Create grails-app/spring/resources.groovy file with the following contents:
    ```
    beans = {
    }
    ```
4.  Run the security init script:
    ```
    ./gradlew runCommand "-Pargs=s2-quickstart grails.security.test SecUser SecRole"
    ```
5.  Edit logging configuration in grails-app/config/logback.xml:
    ```
    <logger name="grails.security.test" level="info" additivity="false">
       <appender-ref ref="STDOUT" />
    </logger>
    ```
6.  Create some sample users/roles in grails-app/init/BootStrap.groovy:
    ```
    SecUser.withTransaction { status ->
      def adminRole = SecRole.findOrSaveWhere( authority: 'ROLE_ADMIN' )
      def userRole = SecRole.findOrSaveWhere( authority: 'ROLE_USER' )

      def adminUser = SecUser.findOrSaveWhere( username: 'admin', password: 'admin' )
      if ( !adminUser.authorities.contains( adminRole ) ) {
        SecUserSecRole.create( adminUser, adminRole, true )
      }

      def regularUser = SecUser.findOrSaveWhere( username: 'user', password: 'user' )
      if ( !regularUser.authorities.contains( userRole ) ) {
        SecUserSecRole.create( regularUser, userRole, true )
      }
    }
    ```
7.  Create an endpoint to be accessible a user with ROLE_USER:
    ``` 
    grails create-controller user
    ```
8.  Edit  grails-app/controllers/grails/security/test/UserController.groovy to resemble the following:
    ```
    package grails.security.test
    
    import grails.plugin.springsecurity.annotation.Secured
    
    @Secured(['ROLE_USER'])
    class UserController {
        def index() {
            [message: "Hello User"]
        }
    }
    ```
9.  Add a GSP view the index action above in grails-app/views/user/index.gsp
    ```
    <html>
        <head>
            <meta name="layout" content="main">
        </head>
        <body>
            <div id="content">
                <h1>${message}</h1>
            </div>
        </body>
    </html>
    ```
10.  Create an endpoint to be accessible a user with ROLE_USER:
    
    ```
    grails create-controller admin
    ```
12.  Edit  grails-app/controllers/grails/security/test/UserController.groovy to resemble the following:
    
    ```
    package grails.security.test
    
    import grails.plugin.springsecurity.annotation.Secured
    
    @Secured(['ROLE_ADMIN'])
    class AdminController {
        def index() {
            [message: "Hello Admin"]
        }
    }
    ```
12.  Add a GSP view the index action above in grails-app/views/user/index.gsp:

    ```
    <html>
        <head>
            <meta name="layout" content="main">
        </head>
        <body>
            <div id="content">
                <h1>${message}</h1>
            </div>
        </body>
    </html>
    ```
