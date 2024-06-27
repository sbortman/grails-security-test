package grails.security.test

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER'])
class UserController {
    def index() {
        [message: "Hello User"]
    }
}