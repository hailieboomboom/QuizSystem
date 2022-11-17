import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Button from '@mui/material/Button';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import OutlinedInput from '@mui/material/OutlinedInput';
import ListItemText from '@mui/material/ListItemText';
import Checkbox from '@mui/material/Checkbox';
import { useState } from 'react';
import { Container } from '@mui/material';
import {Link, useNavigate} from "react-router-dom";
import {useRecoilState} from "recoil";
import {createQuestionOptionsState, createQuestionState} from "../recoil/Atoms";
import axios from "axios";
import {getUserId, getUserRole} from "../utils/cookies.js";
import {apis} from "../utils/apis.js";
import Alert from '@mui/material/Alert';
import EditQuestionOptions from "../components/EditQuestionOptions";
import CreateWrongOptions from '../components/CreateWrongOptions';
import { SettingsInputAntennaTwoTone } from '@mui/icons-material';


const API_URL = 'http://localhost:8088/QuizSystem/api/questions/mcqs'

function CreateQuestion () {

    const navigate = useNavigate();

    // const [editQuestions, setEditQuestions] = useRecoilState(createQuestionState)//from backend

    const [question, setQuestion] = useState('');

    const [tags, setTags] = useState([]);
    const [allTags, setAllTags] = useState([]);

    const [answers, setAnswers] = useState([
      {
       "index": "0",
        "correct": false,
        "id": 0,
        "optionDescription": ""
      },{
        "index": "1",
        "correct": false,
        "id": 0,
        "optionDescription": ""
      },{
        "index": "2",
        "correct": false,
        "id": 0,
        "optionDescription": ""
      }]);

    const [correctAnswer, setCorrectAnswer] = useState(
      {
        "correct": true,
        "id": 0,
        "optionDescription": ""
      });

    const activeUserId = getUserId();
    const activeUserRole = getUserRole();
    const postUrl = API_URL+"/"+activeUserId //change to active user id

    React.useEffect(()=> {
      console.log(answers);
    },[answers]);

    React.useEffect(()=> {
      console.log(allTags);
    },[allTags]);

    React.useEffect(()=> {
      apis.getAllTags()
      .then(response => {
        console.log("tag response:" + response.data)
        if(activeUserRole == "TRAINING") {
          setAllTags((response.data).filter((tag) => tag.toString() !== "interview"))
        }else if(activeUserRole == "AUTHORISED_SALES") {
          setAllTags((response.data).filter((tag) => tag.toString() !== "course"))
        }else {
          setAllTags(response.data)
        }

      })
      .catch((err) =>{
        console.log(err)
      })
    },[]);

    // const counter = 0;

    // const handleSubmit = event => {
    //     console.log('handleSubmit ran');
    //     event.preventDefault(); // 👈️ prevent page refresh

    // };
    // console.log(answers)
    function handleSaveQuestion(){
        console.log(question)

        const data = {
          "options": [correctAnswer, ...answers],
          "questionDetails": question,
          "tags": tags,
          "userId": 1
        }
        axios.post(postUrl, data)
            .then(data => {
                console.log(data)
                navigate('/questions')
            })
            .catch((err) =>{
                console.log(err)
                alert(err.response.data.message)
            });
        
    }

    const addWrongOption = async () => {
      setAnswers(prevState => (
        [...prevState,{
          "correct": false,
          "id": 0,
          "optionDescription": "",
          "index": prevState.length+1
        } ]
      ))
    }

    const deleteWrongOption = async () => {
      if(answers.length>1){
        setAnswers(prevState => ([].concat(prevState.slice(0,-1))
        ))
      }
    }
    // const handleChildChange = async(answerDesciption, index) => {
      
    //   setAnswers((prevState) =>
    //       prevState.filter((oldAnswer) => oldAnswer.index !== index))
    //   ;
    //   setAnswers(prevState => (
    //     [...prevState,{
    //       "correct": false,
    //       "id": 0,
    //       "optionDescription": answerDesciption,
    //       "index": ""+(prevState.length+1)
    //     } ]
    //   ));
    // }

    const handleChange = (event) => {
      setAnswers((prevState) =>
          [].concat(prevState.map((oldAnswer) => oldAnswer.index == event.target.name ? 
          {
            "index": event.target.name,
            "correct": false,
            "id": 0,
            "optionDescription": event.target.value
          }:
          oldAnswer
          )))
      ;
    }

    const handleTagChange = (event) => {
      const {
        target: { value },
      } = event;
      setTags(
        // On autofill we get a stringified value.
        typeof value === 'string' ? value.split(',') : value,
      );
    };

    return (
        <React.Fragment>
            <Container>
                {/* <Typography variant="h6" gutterBottom>
                    Edit Question: {editQuestions.questionDetail} {editQuestions.questionId}
                </Typography> */}

                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        {/* <FormControl variant="standard"  fullWidth>
                            <TextField
                                id="questionId"
                                name="questionString"
                                value={"Question ID: " + editQuestions.questionId}
                                fullWidth
                                variant="standard"
                            />
                        </FormControl> */}
                    </Grid>
                    <Grid item xs={12}>
                        <FormControl variant="standard"  fullWidth>
                            <TextField
                                required
                                id="question"
                                name="questionString"
                                label="Question"
                                fullWidth
                                variant="standard"
                                onChange={event => setQuestion(event.target.value)}
                            />
                        </FormControl>
                    </Grid>
                    <Grid item xs={12}>
                        <FormControl variant="standard"  fullWidth>
                            <TextField
                                required
                                id="correct"
                                name="correctAnswer"
                                label="Correct Answer"
                                fullWidth
                                variant="standard"
                                value={correctAnswer.optionDescription}
                                onChange={event => setCorrectAnswer(
                                  {
                                    "correct": true,
                                    "id": 0,
                                    "optionDescription": event.target.value
                                  })}
                            />
                        </FormControl>
                    </Grid>
                    <Grid item xs={12}>
                        {/* <FormControl variant="standard"  fullWidth>
                            {answers.map((option) => (
                              // <CreateWrongOptions option={option} handleChildChange={handleChildChange}/>
                            ))}
                        </FormControl> */}
                          {answers.map((option) => (
                            <TextField
                              required
                              id="incorrect"
                              name={option.index}
                              label="Incorrect Answer"
                              fullWidth
                              variant="standard"
                              value={option.optionDescription}
                              onChange={handleChange}
                            />

                            // <input type="text" name={option.index} value={option.optionDescription} onChange={handleChange}/>
                          ))}
                    </Grid>
                    
                    <Grid item  xs={12}>
                        {/* {tags.map((usedTag) => (
                          <p>{usedTag}</p>
                        ))} */}
                        <FormControl variant="standard" fullWidth>
                            <InputLabel id="current tags">Add Tag</InputLabel>
                            <Select
                                labelId=""
                                id="tags"
                                multiple
                                value={tags}
                                label="Add Tag"
                                onChange={handleTagChange}
                                input={<OutlinedInput label="Tag" />}
                                renderValue={(selected) => selected.join(', ')}
                            >
                            {allTags.map((tag) => (
                              <MenuItem key={tag} value={tag}>
                                <Checkbox checked={tags.indexOf(tag) > -1} />
                                <ListItemText primary={tag} />
                              </MenuItem>
                            ))}
                            </Select>
                        </FormControl>
                    </Grid>

                </Grid>

                    <Grid item xs={1}>
                        {/*as={Link} to="/successEditQuestion"*/}
                        <Button  onClick={addWrongOption} variant="outlined" >
                            Add Incorrect Answer
                        </Button>
                        <Button  onClick={deleteWrongOption} variant="outlined" >
                            Remove Incorrect Answer
                        </Button>
                        <Button  onClick={handleSaveQuestion} variant="outlined" >
                            Create Question
                        </Button>
                    </Grid>
            </Container>

        </React.Fragment>
    )
}

export default CreateQuestion
