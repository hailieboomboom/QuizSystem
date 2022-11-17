import React, { Fragment, useEffect, useState } from 'react'
import '../styles/profileStyle.css';
import editButton from '../styles/editButton.png';
import profilePicture from '../styles/profilePicture.png';
import downArrow from '../styles/downArrow.png';
import {Link, useNavigate} from "react-router-dom";
import Grid from "@mui/material/Grid";
import {apis} from "../utils/apis.js";
import {getUserId} from "../utils/cookies.js";
import FormDialog from "../components/FormDialog";


const Profile = () => {

  const [username, setUsername] = useState();
  const [email, setEmail] = useState();
  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();
  const [role, setRole] = useState();
  const [openDialog, setOpenDialog] = useState(false);

  const navigate = useNavigate();
  // const [refresh, s]

  useEffect(()=> {
    apis.getUserById(getUserId()).then(
      res => {
        const data = res.data;
        setUsername(data.username);
        setEmail(data.email);
        setFirstName(data.firstName);
        setLastName(data.lastName);
        setRole(data.role);
      }
    )
  }, [])  

  function handleViewYourQuestions(){
    navigate('/questions')
  }

  function closeWindow(){
    setOpenDialog(false);
  }

  const roleMapper = (name) => {
    switch(name){
      case "AUTHORISED_TRAINER":
        return "Trainer";
      case "AUTHORISED_SALES":
        return "Sales";
      case "TRAINING": 
        return "Student(Training)";
      case "POND":
        return "Student(Pond)";
      case "BEACHED":
        return "Student(Beached)";
    }
  }

  return (
      <div className={"profileContainer"}>
          <div className={"profileBox"}>
              <img src={profilePicture} className={"profilePic"}/>
              <img src={editButton} className={"editIcon"} onClick={()=>setOpenDialog(true)}/>
              <h3>{username}</h3>
              <p>Role: {roleMapper(role)}</p>
              {/*<p>Role: {roleMapper(role)}</p>*/}
              <button onClick={handleViewYourQuestions} type={"button"}>View Your Questions</button>
              <div className={"profileBottom"}>
                  <p>First name: {firstName}</p>
                  <p>Last name: {lastName}</p>
                  <p>Email: {email}</p>
              </div>
          </div>
          <div>
            <FormDialog changeCategory={"hidden"} role={roleMapper(role)} openNow={openDialog} closeWindow={closeWindow}></FormDialog>
          </div>
      </div>
  )
}

export default Profile