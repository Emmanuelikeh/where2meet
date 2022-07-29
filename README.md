Application Overview 
===

## Final App idea

# W2M - (where to meet)

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
  - Streamlines how people connect with each other at specific places.


### App Evaluation
- **Category:** Social
- **Mobile:**  This app would be primarily developed for mobile but would perhaps be just as viable on a computer in the future.
- **Story:** Allows users to explore different sites and locations together as pairs or in groups, users have the opportunity to send invites to other users to meet at a particular location or place 
- **Market:** Any individual could choose to use this app, and to ensure privacy only those who are friends have the ability to send invites to other users


## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* .... As a user, i should be able to able to create an account and or signin into an already existing account.
* .... As a user, i should be able to see my profile with pending invitations sent to me 
* ...  As a user i would like to search for places, especially hotspots using the application  [https://developer.foursquare.com/reference/place-search]
* ...  As a user,i want to be able to see a detailed description of search result when i click it  [https://developer.foursquare.com/reference/place-search]
* ... As a user, i should be able to send an invite message to another user to meet at a particular location
* ... As a user i should be able to select "Yes" or "No" to an invitation that another user sends to me 
* ... As a user, i would like all my accepted invitation to be added to my calender and that of the people or persons i invited 
* ... As a user, i would to see a list of my upcoming invitations i accepted

**Optional Nice-to-have Stories**

* [fill in your required user stories here]
* ... As a user, i would like to counter invitations that do not suite me instead of rejecting them.
* ... As a user, i would like to have real time conversations with my friends
* ... As a user, i would like to login in with my google account instead of creating a different account 
* ... As a user, i see a heatmap tracing showing all the places i mostly attend


### 2. Screen Archetypes

* [Login/ Signup screen ] 
    * .... As a user, i should be able to able to create an account and or signin into an already existing account.
          
* [Main screen with bottom navigation and Fragments]
   * [Home Fragment]
       * .... As a user, i should be able to see my profile with pending invitations sent to me 
   * [Search Fragment]
        * ... As a user i would like to search for places, especially hotspots using the application 
        * ... As a user,i want to be able to see a detailed description of search result when i click it  [https://developer.foursquare.com/reference/place-search]
        * ... As a user, i should be able to send an invite message to another user to meet at a particular location
   *  [Calender Fragment]
        * As a user, i would like all my accepted invitation to be added to my calender
        * As a user, i would to see a list of my upcoming invitations i accepted

   

### 3. Navigation

**Tab Navigation** (Tab to Screen)
* login
* Profile
* Search
* Calendar

**Flow Navigation** (Screen to Screen)
* [login]
  * Forced Log-in -> Account creation if no log in is available
* [Profile]
  * Profile -> respond to available invitaions 
* [Search]
  * Search for places -> click on search results -> view detailed search result -> create and invitaion -> send invitation
* [Calendar]
  * see calender -> view upcoming events 

## Wireframes
 * [Link to wireframe](https://www.figma.com/file/iClJtX4fy5BSi7XEYz51K1/where-2-meet-wire-frames?node-id=0%3A1)

## Ambiguous problem writeup 
  * User filtering 
     * When users want to look up other potential friends in the friend search bar, they should have the ability to filter users according to a their defined radius limit and if they have visited at least one similar place present in the application
     * Metadata on filtering process
       * -- User's most recent updated location
       * -- User's marked visted places 
       * -- User's inputed option for consituting of desired radius limitaion and or similarity in liked place
       * -- User's textual input that would be used to get users that fit the defined filtering inputs    
       * -- Results would include users image, name and  a button to send a friendrequest
 * Text messaging 
   * A simple text messaging channel would exist between two people ( person who sends the invite and person who accepts the invite)  and would only be available before the event's due date. 
   * Metadata on text messaging
     * -- Invite title (serving a channel title)
     * -- Invite date (due date before channel becomes unavaialble)
     * -- Invite sender and their associated data (name, and profile image)
     * __ Invite accepter(person who accepts the invite) and their associated data( name and profile image)
     
     
## Schema
### Models





[Invitations]


| Property | Type      | Description|
| -------- | --------  | --------   |
| author   | Pointer to User |invitaion author| 
|authorIMG | File| Image of the author
|Location |String	|Location for users to meet at|
|Time | DateTime| Time of meeting |


[Search Results]


| Property | Type | Description |
| -------- | -------- | -------- |
| fsq_id   | String    | foursquares identifer of the location |
|name|String|name| general name of the place|
|formatted_address| String | address of the loaction|
|icon|image|loactions icon |
|prefix-image|string|image link|
|Description| String | description of a place|
|distance|int |distance from the users location|
|rating | double | crowd sourced rating of the place|
||
|hours | Hours | time of operation |
|website| string | website of the place 



### Networking
- [List of network requests by screen]
   * [Home screen]
     * (Read/GET) Query logged in user object
     * (Update/PUT) Update user profile image
     * (GET)invition Request
     * (Update)invitation Response
   * [Search Screen]
     * (GET) search results and related data
   * [Calender Screen]
     * (GET) upcoming events for a particular user


### Product results 
     

