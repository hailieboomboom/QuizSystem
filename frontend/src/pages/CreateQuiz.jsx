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
import {isLoggedIn,setCookie, deleteCookie, getUserId} from "../utils/cookies"
import { useRecoilState } from 'recoil';
import {createQuizAllQuestions, createQuizSelectedQuestions} from '../recoil/Atoms'
import QuizSelectedQuestionsTable from "../components/QuizSelectedQuestionsTable";
import axios from "axios";
import {useEffect} from "react";
import {apis} from "../utils/apis";
import Autocomplete from "@mui/material/Autocomplete";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";

const CreateQuiz = () => {
    const [category, setCategory] = useState('');
    const [categories, setCategories] = useState([]);
    const [name, setName] = useState('');
    const [quizAllQuestions, setquizAllQuestions] = useRecoilState(createQuizAllQuestions);
    const [quizSelectedQuestions, setquizSelectQuestions] = useRecoilState(createQuizSelectedQuestions);
    const [quizId, setQuizId] = React.useState('');
    const [role, setRole] = useState();
    console.log(getUserId())
    React.useEffect(() => {
        setquizSelectQuestions([]);
        axios.get("http://localhost:8088/QuizSystem/api/questions/quizCreation/mcqs").then((response) => {
            setquizAllQuestions(response.data);
            console.log(response.data);
        }).catch(function (error) {
            console.log(error);
        });
    }, []);
    React.useEffect(()=> {
        if(isLoggedIn()) {
            apis.getRoleByUserId(getUserId()).then(
                res => {
                    console.log("In create, role is: " + res.data["role"])
                    const role = res.data["role"]
                    if(role === "AUTHORISED_TRAINER" || role === "POND" || role === "BEACHED") {
                        setCategories(quizCategoriesA);
                    }
                    else if(role === "TRAINING") {
                        setCategories(quizCategoriesB);
                    }
                    else if(role === "AUTHORISED_SALES") {
                        setCategories(quizCategoriesC);
                    }
                }
            ).catch(
                (err) => {
                    console.log(err)
                }
            )
        }
    }, [role])

    const postQuiz = () => {
        axios.post("http://localhost:8088/QuizSystem/api/quizzes/"+ getUserId() + "", {
            "creatorId": getUserId(),
            "name": name,
            "quizCategory": category
        })
            .then(function (response) {
                postQuestions(response.data.quizId);
                setquizSelectQuestions([]);
            })
            .catch(function (error) {
                console.log(error);
            }).then(function () {
            // always executed
        });
    }
    const postQuestions = (id) => {
        console.log(JSON.stringify(quizSelectedQuestions))
        axios.post("http://localhost:8088/QuizSystem/api/quizzes/" + id + "/questions/" + getUserId() + "",
            quizSelectedQuestions
        )
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error.response.data);
            });

    }

    const handleCreate = () =>{
        postQuiz();
    }

    // const [createQuizSelectedQuestions, setCreateQuizSelectedQuestions] = useRecoilState([]);
    const handleName = (event) => {
        setName(event.target.value);
    };
    const handleCategory = (event) => {
        setCategory(event.target.value);
    };

    const quizCategoriesA = [
        {
            title: "Interview Prep",
            type: "INTERVIEW_QUIZ"
        },
        {
            title: "Course Content",
            type: "COURSE_QUIZ"
        }
    ];
    const quizCategoriesB = [
        {
            title: "Course Content",
            type: "COURSE_QUIZ"
        }
    ];
    const quizCategoriesC = [
        {
            title: "Interview Prep",
            type: "INTERVIEW_QUIZ"
        }
    ];
    if (!isLoggedIn) return(
        <Grid
            container
            justifyContent="center"
            alignItems="center"
        >
            <Grid item>
                <Typography>Please Log in first.</Typography>
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
                                {
                                    categories.map((category) => (
                                        <MenuItem key={category.type} value={category.type} >{category.title}</MenuItem>
                                    ))}

                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid item>
                        <QuizSelectedQuestionsTable/>
                    </Grid>
                    <Grid item xs={1}>
                        <Button onClick={handleCreate} variant="outlined" as={Link} to="/myQuizzes" >
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
