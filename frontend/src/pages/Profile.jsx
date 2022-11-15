import React from 'react'
import '../styles/profileStyle.css'
import Grid from "@mui/material/Grid";

const Profile = () => {
  return (
      <div className={"profileDiv"}>
        <Grid className={"profileGrid"}>
          <Grid item>
            <div className={"profileCenter"}>
              <h1 className={"profileTitle"}>This is your profile!</h1>
            </div>
          </Grid>
        </Grid>
      </div>
  )
}

export default Profile