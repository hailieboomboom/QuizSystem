import * as React from 'react';
import {useState} from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import Button from '@mui/material/Button';
import {Container} from '@mui/material';
import {Link} from "react-router-dom";
import QuizAllQuestionsTable from "../components/QuizAllQuestionsTable";

import { useRecoilState } from 'recoil';
import { createQuizAllQuestions, createQuizSelectedQuestions } from '../recoil/Atoms'
import QuizSelectedQuestionsTable from "../components/QuizSelectedQuestionsTable";
import axios from "axios";

function numQuestions(value) {
    return `${value}°C`;
}

const CreateQuiz = () => {
    const [category, setCategory] = useState('');
    const [difficulty, setDifficulty] = useState('');
    const [type, setType] = useState('');
    const [quizAllQuestions, setquizAllQuestions] = useRecoilState(createQuizAllQuestions);

    React.useEffect(() => {
        axios.get("http://localhost:8088/QuizSystem/api/questions/mcqs/").then((response) => {
            setquizAllQuestions(response.data);
            console.log(quizAllQuestions)
        }).catch(function (error) {
            console.log(error);
        });
    }, []);

    // const [createQuizSelectedQuestions, setCreateQuizSelectedQuestions] = useRecoilState([]);

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
            <Grid
                container
                direction="column"
                justifyContent="flex-start"
                alignItems="center"
            >
                <Grid item>
                <Typography variant="h6" gutterBottom>
                    Create Quiz
                </Typography>
                </Grid>
                <Grid item container
                      alignItems="center"
                      textAlign="left"
                      spacing={3} sx={{width:700}}>
                    <Grid item xs={12}>
                        <FormControl variant="standard" fullWidth>
                            <TextField
                                required
                                id="quizname"
                                name="quizname"
                                label="Quiz name"
                                fullWidth
                                autoComplete="quiz-name"
                                variant="standard"
                            />
                        </FormControl>
                    </Grid>
                    <Grid item  xs={12}>
                        <FormControl variant="standard" fullWidth>
                            <InputLabel id="demo-simple-select-standard-label">Category</InputLabel>
                            <Select
                                labelId="questionCategory"
                                id="questionCategory"
                                value={category}
                                onChange={handleCategory}
                                label="Category"
                            >
                                <MenuItem value="Course Content">Course Content</MenuItem>
                                <MenuItem value="Interview Prep">Interview Prep</MenuItem>
                            </Select>
                        </FormControl>
                    </Grid>
                    {/*<Grid item xs={12}>*/}
                    {/*    <FormControl variant="standard" fullWidth>*/}
                    {/*        <InputLabel id="demo-simple-select-standard-label">Dificulty Level</InputLabel>*/}
                    {/*        <Select*/}
                    {/*            labelId="difficulty"*/}
                    {/*            id="difficulty"*/}
                    {/*            value={difficulty}*/}
                    {/*            onChange={handleDifficulty}*/}
                    {/*            label="Dificulty"*/}
                    {/*        >*/}
                    {/*            <MenuItem value="">*/}
                    {/*                <em>None</em>*/}
                    {/*            </MenuItem>*/}
                    {/*            <MenuItem value={10}>Easy</MenuItem>*/}
                    {/*            <MenuItem value={20}>Medium</MenuItem>*/}
                    {/*            <MenuItem value={30}>Hard</MenuItem>*/}
                    {/*        </Select>*/}
                    {/*    </FormControl>*/}
                    {/*</Grid>*/}
                    {/*<Grid item xs={12}>*/}
                    {/*    <FormControl variant="standard" fullWidth>*/}
                    {/*        <InputLabel id="demo-simple-select-standard-label">Quiz Type</InputLabel>*/}
                    {/*        <Select*/}
                    {/*            labelId="questionType"*/}
                    {/*            id="questionType"*/}
                    {/*            value={type}*/}
                    {/*            onChange={handleType}*/}
                    {/*            label="Quiz Type"*/}

                    {/*        >*/}
                    {/*            <MenuItem value="">*/}
                    {/*                <em>None</em>*/}
                    {/*            </MenuItem>*/}
                    {/*            <MenuItem value={10}>Multiple Choice</MenuItem>*/}
                    {/*            <MenuItem value={20}>True or False</MenuItem>*/}
                    {/*            <MenuItem value={30}>Short Answer</MenuItem>*/}
                    {/*        </Select>*/}
                    {/*    </FormControl>*/}
                    {/*</Grid>*/}
                    <Grid item>
                        <QuizSelectedQuestionsTable/>
                    </Grid>

                    <Grid item xs={1}>

                        <Button variant="outlined" as={Link} to="/viewQuestions">
                            Create
                        </Button>
                    </Grid>

                    <Grid item>
                        <QuizAllQuestionsTable/>
                    </Grid>


                </Grid>
            </Grid>
    )
};

export default CreateQuiz
