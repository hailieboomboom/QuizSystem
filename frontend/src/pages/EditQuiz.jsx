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
import {isLoggedIn,setCookie, deleteCookie, getUserId} from "../utils/cookies"
import {Container} from '@mui/material';
import {Link} from "react-router-dom";
import QuizAllQuestionsTable from "../components/QuizAllQuestionsTable";
import { useRecoilState } from 'recoil';
import {createQuizAllQuestions, createQuizSelectedQuestions } from '../recoil/Atoms'
import QuizSelectedQuestionsTable from "../components/QuizSelectedQuestionsTable";
import axios from "axios";
import CircularProgress from "@mui/material/CircularProgress";
import {Route} from "@mui/icons-material";
import ErrorPage from "./ErrorPage";

const EditQuiz = () => {
    const location = useLocation();
    const { editQuiz } = location.state;
    const [category, setCategory] = useState(editQuiz.quizCategory);
    const [loading, setLoading] = React.useState(true);
    const [name, setName] = useState(editQuiz.name);
    const [dummy, setDummy] = useState('');
    const [eMessage, setEMessage] = React.useState('');
    const [quizAllQuestions, setquizAllQuestions] = useRecoilState(createQuizAllQuestions);
    const [quizSelectedQuestions, setquizSelectQuestions] = useRecoilState(createQuizSelectedQuestions);
    const [quizId, setQuizId] = React.useState('');

    React.useEffect(() => {
        Promise.all([getAllQuestions(), getQuizQuestions()])
            .then(function (results) {
                setquizAllQuestions();
                setquizAllQuestions(results[0].data);
                setquizSelectQuestions(results[1].data);
            }).then(function (results) {

        });
    }, []);

    function getAllQuestions() {
        return axios.get("http://localhost:8088/QuizSystem/api/questions/quizEdit/"+ editQuiz.quizId +"/mcqs")

    }


    function getQuizQuestions() {
        return  axios.get("http://localhost:8088/QuizSystem/api/quizzes/" + editQuiz.quizId + "/questions")
    }

    const updateQuiz = () => {
        axios.put("http://localhost:8088/QuizSystem/api/quizzes/"+editQuiz.quizId+"/"+getUserId()+"", {
            "creatorId": getUserId(),
            "name": name,
            "quizCategory": category,
            "quizId": editQuiz.quizId
        }).then(function (response) {
                updateQuestions(editQuiz.quizId);
                // setquizSelectQuestions([]);
            setEMessage("Success");
            })
            .catch(function (error) {
                console.log(error);
            }).then(function () {
            // always executed
        });
    }
    const updateQuestions = (id) => {
        console.log(JSON.stringify(quizSelectedQuestions))
        axios.put("http://localhost:8088/QuizSystem/api/quizzes/" + id + "/questions/" + getUserId() + "",
            quizSelectedQuestions
        )
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error.response.data);
            });
    }
    const handleUpdate = () => {
        updateQuiz();
    };

    const handleName = (event) => {
        setName(event.target.value);
    };
    const handleCategory = (event) => {
        setCategory(event.target.value);
    };

    console.log(editQuiz);

    if (!editQuiz) return(
        <ErrorPage errorPage="Unauthorized"/>

    );
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
                <Typography variant="h6" gutterBottom>
                    {eMessage}
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
                <Grid item xs={1}>
                    <Button onClick={handleUpdate} variant="outlined">
                        Update
                    </Button>
                </Grid>
                <Grid item>
                    <QuizAllQuestionsTable/>
                </Grid>
            </Grid>
        </Grid>
    )
};

export default EditQuiz
