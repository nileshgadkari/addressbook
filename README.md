How to run the application?  

1. Build an image  
    Run following from main folder with Dockerfile:  
        docker build -t addressbook .  
   
2.  Run following from main folder with docker-compose.yml file:  
        docker-compose up  
    The application needs postgres db. If postgres db image does not exists, it will be pulled automaticall from DockerHub.  
    
Now you should see two containers running:  
    1. postgres running on port 5432  
    2. addressbook ms running on port 8181  

The ensemble is now up and ready for serving API requests.  
