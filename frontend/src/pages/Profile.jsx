import React from 'react'
import '../styles/profileStyle.css'
import editButton from '../styles/editButton.png'
import profilePicture from '../styles/profilePicture.png'
import Grid from "@mui/material/Grid";




const Profile = () => {
  return (
      <div className={"profileContainer"}>
          <div className={"profileBox"}>
              <img src={profilePicture}/>
              <img src={editButton}/>
              <h3>Name</h3>
              <p>Welcome to your personal page!</p>
          </div>

        {/*<Grid className={"profileGrid"}>*/}
        {/*  <Grid item>*/}
        {/*    <div className={"profileCenter"}>*/}
        {/*      <h1 className={"profileTitle"}>This is your profile!</h1>*/}
        {/*    </div>*/}
        {/*  </Grid>*/}
        {/*</Grid>*/}
      </div>

  )
}

export default Profile