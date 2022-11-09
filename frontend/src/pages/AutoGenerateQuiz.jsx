import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import Button from '@mui/material/Button';
import SendIcon from '@mui/icons-material/Send';
import Slider from '@mui/material/Slider';
import { useState } from 'react';
import { Container } from '@mui/material';

function numQuestions(value) {
    return `${value}°C`;
  }
const AutogenerateQuiz = () => {
  const [category, setCategory] = useState('');
  const [difficulty, setDifficulty] = useState('');
  const [type, setType] = useState('');

  const handleCategory = (event) => {
    setCategory(event.target.value);
   
  };
  const handleDifficulty = (event) => {
   
    setDifficulty(event.target.value);
    
  };
  const handleType = (event) => {
  
    setType(event.target.value);
  };
    return (
        <React.Fragment>
            <Container>
  <Typography variant="h6" gutterBottom>
        Autogenerate Create Quiz
      </Typography>
      <Grid container spacing={3}>
        <Grid item xs={12}>
         
<Slider
        aria-label="Temperature"
        defaultValue={30}
        getAriaValueText={numQuestions}
        valueLabelDisplay="auto"
        step={10}
        marks
        min={10}
        max={110}
      />
        </Grid>
       
        <Grid item xs={12}>
        <FormControl variant="standard"  fullWidth>
        <InputLabel id="demo-simple-select-standard-label">Category</InputLabel>
        <Select
          labelId="questionCategory"
          id="questionCategory"
          value={category}
          onChange={handleCategory}
          label="Category"
        >
          <MenuItem value="">
            <em>None</em>
          </MenuItem>
          <MenuItem value={10}>Java</MenuItem>
          <MenuItem value={20}>MySql</MenuItem>
          <MenuItem value={30}>Python</MenuItem>
        </Select>
      </FormControl>
        </Grid>
        <Grid item xs={12}>
        <FormControl variant="standard"  fullWidth>
        <InputLabel id="demo-simple-select-standard-label">Dificulty Level</InputLabel>
        <Select
          labelId="difficulty"
          id="difficulty"
          value={difficulty}
          onChange={handleDifficulty}
          label="Dificulty"
        >
          <MenuItem value="">
            <em>None</em>
          </MenuItem>
          <MenuItem value={10}>Easy</MenuItem>
          <MenuItem value={20}>Medium</MenuItem>
          <MenuItem value={30}>Hard</MenuItem>
        </Select>
      </FormControl>
        </Grid>
        <Grid item xs={12} >
        <FormControl variant="standard"  fullWidth>
        <InputLabel id="demo-simple-select-standard-label">Quiz Type</InputLabel>
        <Select
          labelId="questionType"
          id="questionType"
          value={type}
          onChange={handleType}
          label="Quiz Type"
          
        >
          <MenuItem value="">
            <em>None</em>
          </MenuItem>
          <MenuItem value={10}>Multiple Choice</MenuItem>
          <MenuItem value={20}>True or False</MenuItem>
          <MenuItem value={30}>Short Answer</MenuItem>
        </Select>
      </FormControl>
        </Grid>
        <Grid item xs={1}>
       
      <Button variant="outlined" endIcon={<SendIcon />}>
        Create
      </Button>
        </Grid>
     
    
      </Grid>
            </Container>
    
     
    </React.Fragment>
    )
}

export default AutogenerateQuiz
