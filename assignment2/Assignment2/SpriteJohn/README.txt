NOTE - YOU MAY HAVE TO CLEAR CACHE EVERYTIME YOU ENTER PASSWORD
	on ERROR OR LOGIN

FOR BASIC AUTH

Make sure to have appuser users 
	with group name "Admin" or "RestGroup" or "JsfGroup"


=======================================================================

TO TEST FORM AUTH

go to ejb ->
	Source Packages ->
			entity ->
				-AppUserFacade
				-SpriteFacade
				//comment the declared roles and Roles allowed

go to ejb ->
	Source Packages ->
			service ->
				ApplicationCOnfig
				//comment all annotation on class EXCEPT APplicationPATH

got to war ->
	Web Pages ->
		 WEB-INF ->
			web.xml
			//comment out first 2 section of security constraint
			and uncomment <login-config> section with security constraint
				