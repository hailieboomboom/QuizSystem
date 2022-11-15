import React, { useState } from 'react'
import {apis} from "../utils/apis.js";

const Profile = () => {
  const [username, setUsername] = useState();
  const test = () => {
    apis.authorizeTrainer(username).then(
      res => {
        console.log(res.data)
      }
    )
  }
  return (
    <div><h1>Profile</h1></div>
  )
}

export default Profile