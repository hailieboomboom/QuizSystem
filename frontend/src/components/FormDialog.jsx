import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import {useState, useEffect} from 'react';
import {apis} from '../utils/apis';
import {getUserId} from '../utils/cookies';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import { Box } from '@mui/system';


export default function FormDialog(props) {
 
    const [email, setEmail] = useState(null);
    const [firstName, setFirstName] = useState(null);
    const [lastName, setLastName] = useState(null);
    const [password, setPassword] = useState(null);
    const [open, setOpen] = useState(false);
    const [changeCategory, setChangeCategory] = useState(false);
    const [category, setCategory] = useState(null);

    useEffect(()=>{
        setOpen(props.openNow)
        setChangeCategory(props.changeCategory)
    }, [props.openNow])

    const handleClose = () => {
        setOpen(false);
        props.closeWindow();
    };

    const handleCategoryChange = (event) => {
        setCategory(event.target.value)
    }

    const handleSubmit = () => {
        console.log("update info...")
        mapEmptyToNull()
        switch(props.role) {
            case "Trainer":
                apis.updateTrainerInfo(getUserId(), password, email, firstName, lastName).then(
                    res => {
                        console.log(res.data)
                        // props.handleRefresh();
                        setOpen(false);
                        props.closeWindow();
                    }
                ).catch(
                    err => console.log(err)
                )
                break;
            case "Sales": 
                apis.updateSalesInfo(getUserId(), password, email, firstName, lastName).then(
                    res => {
                        console.log(res.data);
                        setOpen(false);
                        props.closeWindow();
                    }
                ).catch(
                    err => console.log(err)
                )
                break;
            case "Student(Training)":
            case "Student(Pond)":
            case "Student(Beached)":
                apis.updateStudentInfo(getUserId(), password, email, firstName, lastName).then(
                    res => {
                        console.log(res.data);
                        setOpen(false);
                        props.closeWindow();
                    }
                ).catch(
                    err => console.log(err)
                )
                break;
            default:
                if(changeCategory) {
                    apis.updateStudentInfoWithRole(props.studentId, password, email, firstName, lastName, category).then(
                        res => {
                            console.log(res.data)
                            // props.handleRefresh();
                            setOpen(false);
                            props.closeWindow();
                        }
                    ).catch(
                        err => console.log(err)
                    )
                }
                else {
                    console.log("No role is matched!(FormDialog)")
                }
                
        }
    }

    const mapEmptyToNull = () => {
        if(email === '') {
            setEmail(null)
        }
        if(firstName === '') {
            setFirstName(null)
        }
        if(lastName === '') {
            setLastName(null)
        }
        if(password === '') {
            setPassword(null)
        }
        if(category === '') {
            setCategory(null)
        }
    }

    return (
    <div>
        <Dialog open={open} onClose={handleClose}>
            <DialogTitle>Update</DialogTitle>
        <DialogContent>
            <TextField
            autoFocus
            margin="dense"
            id="email"
            label="Email"
            type="text"
            fullWidth
            variant="standard"
            onChange={e => setEmail(e.target.value)}
            />
            <TextField
            autoFocus
            margin="dense"
            id="password"
            label="Password"
            type="password"
            fullWidth
            variant="standard"
            onChange={e => setPassword(e.target.value)}
            />
            <TextField
            autoFocus
            margin="dense"
            id="firstName"
            label="First name"
            type="text"
            fullWidth
            variant="standard"
            onChange={e => setFirstName(e.target.value)}
            />
            <TextField
            autoFocus
            margin="dense"
            id="lastName"
            label="Last name"
            type="text"
            fullWidth
            variant="standard"
            onChange={e => setLastName(e.target.value)}
            />
            {/* // visible or hidden */}
            <Box sx={{visibility: changeCategory}}>
                <FormControl sx={{ mt: 3, minWidth: 120}}>
                <InputLabel htmlFor="category">Category</InputLabel>
                <Select
                    autoFocus
                    label="category"
                    value={category}
                    onChange={handleCategoryChange}
                    inputProps={{
                        name: 'max-width',
                        id: 'max-width',
                    }}
                >
                    <MenuItem value="TRAINING">Training</MenuItem>
                    <MenuItem value="POND">Pond</MenuItem>
                    <MenuItem value="BEACHED">Beached</MenuItem>
                    <MenuItem value="ABSENT">Absent</MenuItem>
                </Select>
                </FormControl>
            </Box>
        </DialogContent>
        <DialogActions>
            <Button onClick={handleClose}>Cancel</Button>
            <Button onClick={handleSubmit}>Submit</Button>
        </DialogActions>
        </Dialog>
    </div>
    );
}