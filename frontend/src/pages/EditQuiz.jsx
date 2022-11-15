import * as React from 'react';
import {useState} from 'react';
import { useLocation } from 'react-router-dom'
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
import {createQuizAllQuestions, createQuizSelectedQuestions} from '../recoil/Atoms'
import QuizSelectedQuestionsTable from "../components/QuizSelectedQuestionsTable";
import axios from "axios";
import CircularProgress from "@mui/material/CircularProgress";

const EditQuiz = () => {
    const location = useLocation();
    const { editQuiz } = location.state;
    const [category, setCategory] = useState(editQuiz.quizCategory);
    const [loading, setLoading] = React.useState(true);
    const [name, setName] = useState(editQuiz.name);
    const [dummy, setDummy] = useState('');
    const [quizAllQuestions, setquizAllQuestions] = useRecoilState(createQuizAllQuestions);
    const [quizSelectedQuestions, setquizSelectQuestions] = useRecoilState(createQuizSelectedQuestions);
    const [quizId, setQuizId] = React.useState('');

    React.useEffect(() => {
        quizAllQuestions.forEach(function get(currentValue) {
            quizSelectedQuestions.forEach(function countEntry(entry) {
                if(currentValue.questionId === entry.questionId){
                    setquizAllQuestions((questions) =>
                        questions.filter((question) => question.questionId !== currentValue.questionId)

                    );
                }
            })
        })
    }, []);
    //
    //     const filterAll = () => {
    //         results[0].data.forEach(function get(currentValue) {
    //             console.log(currentValue);
    //             results[1].data.forEach(function countEntry(entry) {
    //                 if(currentValue.questionId === entry.questionId){
    //                     setquizAllQuestions((questions) =>
    //                         questions.filter((question) => question.questionId !== currentValue.questionId)
    //
    //                     );
    //                 }
    //             })
    //         })
    //     }
    //
    //
    //
    // function getAll(currentValue) {
    //     console.log(currentValue);
    //     console.log(this);
    //
    //
    // }
    //
    // function getAllQuestions() {
    //     return axios.get("http://localhost:8088/QuizSystem/api/questions/quizCreation/mcqs")
    //
    // }
    //
    //
    // function getQuizQuestions() {
    //     return  axios.get("http://localhost:8088/QuizSystem/api/quizzes/"+ editQuiz.quizId +"/questions")
    // }



    const handleName = (event) => {
        setName(event.target.value);
    };
    const handleCategory = (event) => {
        setCategory(event.target.value);
    };

    console.log(editQuiz);

    if (!quizAllQuestions) return(
        <Grid
            container
            justifyContent="center"
            alignItems="center"
        >
            <Grid item>
                <Typography>Please Wait...</Typography>
                <CircularProgress />
            </Grid>
        </Grid>

    );
    return (
        <Grid
            container
            direction="column"
            justifyContent="flex-start"
            alignItems="center"
        >
            <Grid item>
                <Typography variant="h6" gutterBottom>
                    Edit Quiz
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
                            value={name}
                            onChange={handleName}
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
                            <MenuItem value="COURSE_QUIZ">Course Content</MenuItem>
                            <MenuItem value="INTERVIEW_QUIZ">Interview Prep</MenuItem>
                        </Select>
                    </FormControl>
                </Grid>
                <Grid item>
                    <QuizSelectedQuestionsTable/>
                </Grid>
                <Grid item>
                    <QuizAllQuestionsTable/>
                </Grid>
            </Grid>
        </Grid>
    )
};

export default EditQuiz
