import { Typography, Table, TableBody, TableCell, TableHead, TableRow, Button} from '@material-ui/core';
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import ApiService from '../ApiService'

export default function MemberList() {
    const navigate = useNavigate();
    const [members, setMembers] = useState([]);
    const [message, setMessage] = useState(null);

    useEffect(() => reloadMemberList,[]);

    const reloadMemberList = () => {
        ApiService.fetchMembers()
            .then(res=>{
                setMembers(res.data.data)
            })
            .catch(error => {
                console.log("reloadMemberList() Error!!", error);
            })
    }

    const deleteMember = (memberId) => {
        ApiService.deleteMember(memberId)
            .then(res => {
                setMessage('Member delete')
                console.log(message)
            })
            .catch(error => {
                console.log("deleteMember() Error!!", error);
            })
        window.location.reload();
    }

    const editMember = (memberId) => {
        window.localStorage.setItem("memberId", memberId);
        navigate('/edit-member/'+memberId)
    }

    const addMember = () => {
        navigate('/add-member')
    }

    return (
        <div>
            <Typography variant='h4' style={style}>MemberList</Typography>
                <Button variant='contained' color='primary' onClick={addMember}> Add Member</Button>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell align='center'>ID</TableCell>
                        <TableCell align='center'>NAME</TableCell>
                        <TableCell align='center'>PASSWORD</TableCell>
                        <TableCell align='center'>AGE</TableCell>
                        <TableCell align='center'>SALARY</TableCell>
                        <TableCell >BUTTON</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {members.map(member => 
                        <TableRow component='th' scope='member' key={member.memberId}>
                            <TableCell align='center'>{member.memberId}</TableCell>
                            <TableCell align='center'>{member.memberName}</TableCell>
                            <TableCell align='center'>{member.password}</TableCell>
                            <TableCell align='center'>{member.age}</TableCell>
                            <TableCell align='center'>{member.salary}</TableCell>
                            <TableCell align='center'>
                                <Button align='right' onClick={() => editMember(member.memberId)}>
                                    update
                                </Button>
                                <Button align='right' onClick={() => deleteMember(member.memberId)}>
                                    delete
                                </Button>
                            </TableCell>
                        </TableRow>
                        )
                    }
                </TableBody>
            </Table>
        </div>
    );
}

const style = {
    display: 'flex',
    justifyContent: 'center'
}