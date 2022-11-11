import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import { Link } from "react-router-dom";
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import {useState} from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function SignInSide() {
    const navigate = useNavigate();
    const[username,setUsername] = useState('');
    const[password,setPassword] = useState('');
    const [errorMsg, setErrorMsg] = useState(null);

  const handleClick = (event) => {
      setErrorMsg("");
    event.preventDefault();
      const user={password,username}
      console.log(user)
      fetch("http://localhost:8088/QuizSystem/auth/login",{
          method:"POST",
          headers:{"Content-Type":"application/json"},
          body:JSON.stringify(user)

      }).then((res)=>{
          if(res.status === 200) return res.text();
          else if(res.status === 401 || res.status === 403){
              setErrorMsg("Invalid username or password");
          }else {
              setErrorMsg(
                  "Something went wrong, try again later or reach out to trevor@coderscampus.com"
              );
          }
          console.log("Hello Student Logged in")
      })
          // .then((data) => {
          //     if (data) {
          //         user.setJwt(data);
          //         navigate("/dashboard");
          //     }
          // });
  };

  return (
      <form noValidate autoComplete="off">
      <Grid container component="main" sx={{ height: '100vh' }}>
        <CssBaseline />
        <Grid
          item
          xs={false}
          sm={4}
          md={7}
          sx={{
            backgroundImage: 'url(https://images.unsplash.com/photo-1618005198919-d3d4b5a92ead?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1074&q=80)',
            backgroundRepeat: 'no-repeat',
            backgroundColor: (t) =>
              t.palette.mode === 'light' ? t.palette.grey[50] : t.palette.grey[900],
            backgroundSize: 'cover',
            backgroundPosition: 'center',
          }}
        />
        <Grid item xs={12} sm={8} md={5} component={Paper} elevation={12} square>
          <Box
            sx={{
              my: 8,
              mx: 4,
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
            }}
          >
            <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
              <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
              Sign in
            </Typography>

                <TextField
                    required
                    fullWidth
                    id="username"
                    label="Username"
                    name="username"
                    autoComplete="username"
                    value={username}
                    onChange={(e)=>setUsername(e.target.value)}
                />
              <TextField
                margin="normal"
                required
                fullWidth
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="new-password"
                value={password}
                onChange={(e)=>setPassword(e.target.value)}
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
                onClick={handleClick}
              >
                Sign In
              </Button>
                {username}
                {password}
              <Grid container>
                <Grid item xs>
                  <Link href="#" variant="body2">
                    Forgot password?
                  </Link>
                </Grid>
                <Grid item>
                  <Link to="/register" variant="body2">
                    {"Don't have an account? Sign Up"}
                  </Link>
                </Grid>
              </Grid>
          </Box>
        </Grid>
      </Grid>
      </form>
  );
}