# SocketProgramming

## Exercises - HTTP

2. Monitoring HTTP Headers 2
* Telling the browser what to expect. (file format etc)

4. Get HTTP Request Headers on the Server
![screenShot](https://i.imgur.com/8NWrasn.png)

5. Get/Post-parameters
* With the GET method it displays the parameters in the URL. There is also a lot more data in the request header when we are using POST instead of get. And instead of it being a Query string (with GET method) it’s now called form data (with POST method)

6. Sessions (Session Cookies) 
* Sessions (session cookies) gets terminated when you are restarting your browser since the server does not save and collect them. So when the server needs a session (cookie) it ask the client if it exist and then gets it from the client.

7. Persistent Cookies
* Persistent Cookies work a bit as the same way as Session cookies. It's still only stored on the client but when has a expiration linked to it. So it works even tho your computer has been shutdown as long as the date hasn’t passed yet.

## Network Exam Preparation

1. What is the ip address of your wireless card?
* 10.50.130.31

2. What is special about the IP-addresses that starts with 10 (and 172.16 and 192.168)
* local/private ip

3. Who or what gave you that address?
* The router

4. What is a DHCP server (conceptually)
* Controlling the ip’s and giving out the ip’s

5. What is the address of your DHCP Server
* 10.255.1.9

6. What is a DNS server (conceptually)
* It’s a server that tells you the IP address of a domain.

7. What is the DNS server address?
* 10.250.1.1 / 10.250.1.2

8. What is a MAC address
* MAC address is a unique hardware IP that your internet device have so you can pinpoint specific computers instead of just the open IP.

9. What is the MAC address of the your Network Interface(s)?
* A4-4E-31-D1-F7-D5
	
10. How many network interfaces do you have?
* 7

11. Why do you have more than one? What are they for?
* They are for Virtual Machines or VPN connections, I also have 1 ethernet port and 1 wireless network card

12. What is your public address (WAN) (can’t be found with ipconfig)  address right now. Ask others in the class for theirs, do you all have the same public address ?
* We have different ip addresses since the school has a IP-range as their outgoing traffic, probably so services doesn’t block the school ip as DDOS.



## Networking

For these exercises you need to use the tools (figure out which ones): ping, netstat, whois, traceroute (windows) or traceroute (linux)

1. Find the IP address for cphbusiness.dk
* 195.254.168.52

2. When was cphbusiness.dk registered first time and whois the Registrant
* 15 december 2008, HKN40-DK

3. Use ping to verify whether you Digitalocean Droplet is online
* 207.154.212.232

4. How many routers do you need to go through to reach dr.dk?
* 7

5. How many routers do you need to go through to reach rhcloud.com?
* 19
 
6. How many active connections do you have on your computer?
* ??

7. What is the round-trip time to reach google.com?
* 33 ms

8. Why is it so low if Google is in the United States?
* Because google also has servers in europe.

9. Start your local Tomcat server and use netstat to verify whether “anyone” is listening on port 8080 and 3306 (what would you expect to find listening on 3306?)
* mySQL

10. If you have setup MySQL on Digital Ocean to be accessible from the school (or home), verify this using Telnet. If not, ask around in the class, and find one who has.
* I gain access when writing telnet <server ip> <port> in my bash terminal


Domain Name System
For some of these exercises you can use the command line tool nslookup

Find the IP address for your domain name (won’t work, unless you have completed the steps below)
ping www.alfen.me in cmd which gives the IP: 207.154.212.232

Set up your HostName to point to your droplet(s) as explained here in the steps:
Configuring your Domain Part-1,  Change your Domain Server,  Configuring your Domain Part-2
Wait some time (why) perhaps an hour, and repeat step 2.
Verify that your droplet(s) can be using your domain name


# CA2Chat
