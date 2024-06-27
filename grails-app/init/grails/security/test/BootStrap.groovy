package grails.security.test

import groovy.util.logging.Slf4j
import org.springframework.context.MessageSource

@Slf4j
class BootStrap {
  def init = { servletContext ->
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
  }

  def destroy = {
  }
}