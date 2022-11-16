import * as React from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Button from '@mui/material/Button';
import { useState } from 'react';
import { Container } from '@mui/material';
import { Link } from "react-router-dom";
import {useRecoilState} from "recoil";
import {createQuestionOptionsState, createQuestionState} from "../recoil/Atoms";
import axios from "axios";
import EditQuestionOptions from "../components/EditQuestionOptions";
import CreateWrongOptions from '../components/CreateWrongOptions';
import { SettingsInputAntennaTwoTone } from '@mui/icons-material';
const API_URL = 'http://localhost:8088/QuizSystem/api/questions/mcqs'

function CreateQuestion () {
    const [editQuestions, setEditQuestions] = useRecoilState(createQuestionState)//from backend

    const [question, setQuestion] = useState('');

    const [tags, setTag] = useState([]);
    const [answers, setAnswers] = useRecoilState(createQuestionOptionsState);

    const url = `${API_URL}/${editQuestions.questionId}`

    const counter = 0;

    const handleSubmit = event => {
        console.log('handleSubmit ran');
        event.preventDefault(); // 👈️ prevent page refresh

    };
    // console.log(answers)
    function handleSaveQuestion(){


        console.log(question)

        const data = {

            "options": answers,
            "questionDetails": question,
            "tags": tags,
            "userId": 0

        }
        axios.put(url, data)
            .then(data => {
                console.log(data);
            })
            .catch((err) =>{
                console.log(err)
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

    const handleChildChange = async(answerDesciption, index) => {
      
      setAnswers((prevState) =>
          prevState.filter((oldAnswer) => oldAnswer.index !== index))
      ;
      setAnswers(prevState => (
        [...prevState,{
          "correct": false,
          "id": 0,
          "optionDescription": answerDesciption,
          "index": prevState.length+1
        } ]
      ));
    }

    React.useEffect(()=> {
      console.log(answers);
    },[answers]);




    return (
        <React.Fragment>
            <Container>
                {/* <Typography variant="h6" gutterBottom>
                    Edit Question: {editQuestions.questionDetail} {editQuestions.questionId}
                </Typography> */}

                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <FormControl variant="standard"  fullWidth>
                            <TextField
                                id="questionId"
                                name="questionString"
                                value={"Question ID: " + editQuestions.questionId}
                                fullWidth
                                variant="standard"
                            />
                        </FormControl>
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
                            {answers.map((option) => (
                              <CreateWrongOptions option={option} handleChildChange={handleChildChange}/>
                            ))}
                        </FormControl>
                    </Grid>

                </Grid>

                    <Grid item xs={1}>
                        {/*as={Link} to="/successEditQuestion"*/}
                        <Button  onClick={addWrongOption} variant="outlined" >
                            Add Incorrect Answer
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
