import React from 'react'
import {Stack} from "@mui/material";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import BG from "../media/bg1.png";

const Hero = () => {
    return (
       <Box>

           <Box sx={{
               backgroundImage: `url(${BG})`,
               backgroundRepeat: "no-repeat",
               backgroundPosition: "center",
               backgroundSize:"cover",
               height: 900,
               width:"100%"
           }}>
               <Typography align="center" variant={"h3"} sx={{fontWeight:900}}>
                   Welcome to Quiz App
               </Typography>

           </Box>

       </Box>
    )
}

export default Hero
