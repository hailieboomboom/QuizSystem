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
import {editQuestionOptionsState, editQuestionState} from "../recoil/Atoms";
import axios from "axios";
import EditQuestionOptions from "../components/EditQuestionOptions";
const API_URL = 'http://localhost:8088/QuizSystem/api/questions/mcqs'

const EditQuestion = () => {
    const [editQuestions, setEditQuestions] = useRecoilState(editQuestionState)

    const [question, setQuestion] = useState('');

    const [answers, setAnswers] = useRecoilState(editQuestionOptionsState);

    const url = `${API_URL}/${editQuestions.questionId}`

    const counter = 0;

    const handleSubmit = event => {
        console.log('handleSubmit ran');
        event.preventDefault(); // 👈️ prevent page refresh

    };
    console.log(answers)
    function handleSaveQuestion(){
        console.log(question)

        const data = {

            "options": answers,
            "questionDetails": question,
            "tags": [
            "interview"
        ],
            "userId": 1

        }
        console.log(answers)
        axios.put(url, data)
            .then(data => {
                console.log(data);
            })
            .catch((err) =>{
                console.log(err)
            });

        console.log(data)
    }



    return (
        <React.Fragment>
            <Container>
                <Typography variant="h6" gutterBottom>
                    Edit Question: {editQuestions.questionDetail} {editQuestions.questionId}
                </Typography>

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
                            {editQuestions.mcqOptionDtoList.map((option) => (
                                <EditQuestionOptions option={option}/>
                            ))}

                        </FormControl>
                    </Grid>

                </Grid>

                    <Grid item xs={1}>
                        {/*as={Link} to="/successEditQuestion"*/}
                        <Button  onClick={handleSaveQuestion} variant="outlined" >
                            Edit Question
                        </Button>
                    </Grid>
            </Container>

        </React.Fragment>
    )
}

export default EditQuestion
