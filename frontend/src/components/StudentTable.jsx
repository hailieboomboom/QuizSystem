import * as React from 'react';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import { apis } from '../utils/apis';
import {useState, useEffect} from "react";
import Button from '@mui/material/Button';
import FormDialog from './FormDialog';


export default function StudentTable(){
    const [students, setStudents] = useState([]);
    const [openDialog, setOpenDialog] = useState(false);
    const [studentId, setStudentId] = useState();

    useEffect( ()=> {
        apis.getAllStudents()
        .then(resp => {
            setStudents(resp.data)
            console.log(resp.data)
        }).catch(
            err => console.log(err)
        )
    }, [])

    const closeWindow = () => {
        setOpenDialog(false);
    }

    return (
        <div>
            <TableContainer component={Paper}>
                <Table aria-label="simple table" stickyHeader>
                    <TableHead>
                        <TableRow>
                            <TableCell >Username</TableCell>
                            <TableCell >First Name</TableCell>
                            <TableCell >Last Name</TableCell>
                            <TableCell >Email</TableCell>
                            <TableCell >Role</TableCell>
                            <TableCell >Authorize</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {students.map((student) => (
                            <TableRow key={student.username}>
                                <TableCell component="th" scope="row" key={student.username}>
                                    {student.username}
                                </TableCell>
                                <TableCell >{student.firstName}</TableCell>
                                <TableCell >{student.lastName}</TableCell>
                                <TableCell >{student.email}</TableCell>
                                <TableCell >{student.role}</TableCell>
                                <TableCell >
                                    <Button variant="outlined" onClick={()=>{setOpenDialog(true); setStudentId(student.id)}}>Edit</Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <FormDialog changeCategory={"visible"} studentId={studentId} openNow={openDialog} closeWindow={closeWindow}></FormDialog>
        </div>
        
    )
}