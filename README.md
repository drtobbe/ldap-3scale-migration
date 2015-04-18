# README #

### LDAP -> 3Scale Portal Migration

	$ vi src/main/java/com/ldap/threescale/Constants.java
	$ cat csv/LDAPcontent.ldif
	$ mvn install
	$ mvn exec:exec

### Mapping: LDAP -> 3Scale 

	cn 		-> org_name
	mail 	-> username
	mail 	-> email
	consumerId -> user_key
	UUID.randomUUID() -> password
	associatedDomainName -> last_name
	
	cn + "'s App" -> name
	"Description of your default application" -> description
	