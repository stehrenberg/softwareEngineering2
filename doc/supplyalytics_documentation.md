#SupplyAlytics Application
___

##_User Documentation_
 _Built by ApachenPubTeam: Kevin Beck, Stephanie Ehrenberg, Simon Holzbein, Maximilian Öckler, Maximilian Renk_


__SupplyAlytics is a small tool to compare suppliers based on their delivery behavior. It provides two graphs for illustrating the reliability of the company's suppliers, each with a slightly different focus concerning the displayed data. It also comes with a user management system that allows multiple users to use the application. __
___
###General Application Navigation
The application's navigation bar is kept neat and simple. 
Under the menu item "File" are general functions to be found such as "Logout" and "PDF Export" (a future feature that is not implemented in the beta product).

After clicking on the menu item "Perspectives" you are able to select the two graph views to visualize and analyze suppliers' delivery behavior. "Perspectives" --> "Preferences" allows you to set the threshold values for regarding a delivery as "very early", "early", "late" and so on.

"User" provides you with additional features to manage, delete and add users (if you are the admin user) or at least manage your own data. You can also log out of the application.

"Help" is (as of current beta testing state) void of functionality. In the end product it will at least display further information on the developer team.

Here is a general chart of the application menu.
![activity_diagram__user_menu](./activity_diagrams/activity_diagram__user_menu.png =800x)

---
###User Management Features###
SupplyAlytics has implemented several features to enable creation and management of the application's users. The available functionality differs from admin user to normal user.  

On the first initial application start, no application users are registered and thus existant yet and a former
![register_form](supplyalytics_register_user.png =320x240)


    **Important notes:**  
	As soon as an admin user is registered, there is no possibility for additional users to register themselves - the creation of a new user is a feature provided only for admin users to limit the access to the production data.  
	Please take note that during the beta stage of our application, it is only possible for further users to log into the application on the admin's machine.

---
####Change Own Userdata
It is always possible to change your own user data, even if you are not the admin user. If you wish to do so, click on the menu bar item "User" and choose "Settings":

![change_own_settings](supplyalytics_change_own_settings.png =250x)  

Your user information includes your forename, surname, email address, the chosen username and your password. To save your new settings, click the "Update" button at the bottom.

![change_settings_form](supplyalytics_changeOwnData_form.png =320x240)

The following activity chart illustrates the single steps to change your own user data in more detail:
![activity_diagram__change_own_data](./activity_diagrams/activity_diagram__changeOwnData.png =600x)

---
####Admin Feature: Change Data of Other Users

As the admin user you are privileged to manage other users' data (and change their passwords once they forgot it). With great powers comes great responsibility, though.  

The functionality is accessible via menu item "User" --> "Manage all Users":

![user_menu_manage_users](supplyalytics_user_menu_manageAllUsers.png =250x)

Here you can select the user whose data you want to change. Make sure you click the "Update" button after changing data.  
![manage_users](supplyalytics_manage_all_users.png =320x240)


    **Important note:**
    It is also possible to delete a user via "Delete" button.
    Be cautious with that one. You will get a prompt asking if you really want to delete the respective user if you happen to click on this button accidentally.
    
To better understand the multiple steps that lead up to a change of user data, have a look at the following activity diagram:

 ![activity_diagram__change_user_data](./activity_diagrams/activity_diagram__changeUserData.png =600x)

---

####Admin Feature: Create New User

Another admin's responsibility is the creation of additional users that wish to use the SupplyAlytics application.  
As mentioned earlier, it is not possible for new users to register themselves, so in case you have reasons to use the app, but do not have an account yet, contact your system administratior concerning the creation of an account.  

This feature is accessible via menu item "Users" --> "New User". You are then provided with a form were you can enter the new user's data. Upon clicking the "Register" Button you create the new user.

    **Important Note:**
    As mentioned above, during beta testing phase it is only possible for new users to use the app on the admin's machine.

![add_new_user](supplyalytics_registrationForm_additional_user.png =320x240)

This chart illustrates the general activity flow during the creation of a new user:
 ![activity_diagram__create_new_user](./activity_diagrams/activity_diagram__createNewUser.png =600x)