import React from 'react'
import '../styles/profileStyle.css'
import editButton from '../styles/editButton.png'
import profilePicture from '../styles/profilePicture.png'
import downArrow from '../styles/downArrow.png'

import Grid from "@mui/material/Grid";




const Profile = () => {
  return (
      <div className={"profileContainer"}>
          <div className={"profileBox"}>
              <img src={profilePicture} className={"profilePic"}/>
              <img src={editButton} className={"editIcon"}/>
              <h3>Name</h3>
              <p>Welcome to your personal page!</p>
              <button type={"button"}>View Your Questions</button>
              <div className={"profileBottom"}>
                  <p>More information:</p>
                  <img className={"downArrow"} src={downArrow}/>
              </div>
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