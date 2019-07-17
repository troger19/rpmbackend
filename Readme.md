heroku login
Enter your Heroku credentials.
Email: janci.babel@gmail.com
Password: UeNnM0a-

heroku create

git push heroku master
//if heroku git repository is not set to the correct one, you can easily add it
//heroku git:remote -a rpmbackend


heroku open

// only for the first time.. add postress support
heroku addons:create heroku-postgresql
//see some postgress configurations
heroku config
heroku pg


https://rpmbackend.herokuapp.com/


Connect to database on Heroku:
Go to heroku.com, Click on Heroku Postres  -> Settings -> Database Credentials

Heroku CLI -> Execute in Commnad line
heroku pg:psql postgresql-acute-84772 --app rpmbackend
select * from person;



Backup PostreSQL database
heroku pg:backups:capture -a rpmbackend
heroku pg:backups:download -a rpmbackend

Dump is located in directory from which you execute the command.
