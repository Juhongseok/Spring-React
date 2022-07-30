import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import ApiService from '../ApiService';

import {TextField, Button, Typography} from '@material-ui/core'

export default function EditMember() {
    const navigate = useNavigate()
    const [memberId, setMemberId] = useState(window.location.pathname.split('/')[2])
    const [salary, setSalary] = useState('');
    const [message, setMessage] = useState('')

    useEffect(() => loadMember, []);

    const loadMember = () => {
        ApiService.fetchMemberById(memberId)
            .then(res => {
                let member = res.data.data;
<<<<<<< HEAD

                setMemberId(member.memberId);
                setSalary(member.salary);
=======
                this.setState({
                    memberId:member.memberId,
                    salary:member.salary
                })
                window.localStorage.removeItem('membeRId')
>>>>>>> 1ec8ca5673bbeacd182825de8d41e02853a92dc0
            })
            .catch(error => {
                console.log('loadMember() Error', error);
            })
    }

    const onMemberIdChange = (e) => {
        setMemberId(e.target.value);
    }

    const onSalaryChange = (e) => {
        setSalary(e.target.value);
    }

    const updateMember = (e) => {
        e.preventDefault();

<<<<<<< HEAD
        let member = {
            id:memberId,
            salary:salary
=======
        let updateMember = {
            id:this.state.memberId,
            salary:Number(this.state.salary)
>>>>>>> 1ec8ca5673bbeacd182825de8d41e02853a92dc0
        }

        ApiService.updateMember(updateMember)
            .then(res => {
<<<<<<< HEAD
                setMessage(member.memberId + '님 정보가 수정되었습니다.')
                console.log(message)
=======
                this.setState({
                    message : updateMember.id + '님 정보가 수정되었습니다.'
                })
>>>>>>> 1ec8ca5673bbeacd182825de8d41e02853a92dc0
            })
            .catch(error => {
                console.log('updateMember() Error', error);
            })

        navigate('/')
    }

    return (
        <div>
            <Typography variant='h4' style={style}>Edit Member</Typography>
            <form>
                <TextField type="text" placeholder='input Id' name='memberId'
                    fullWidth value={memberId} onChange={onMemberIdChange}/>
                <TextField type="number" placeholder='input salary' name='salary'
                    fullWidth value={salary} onChange={onSalaryChange}/>
                <Button variant='contained' color='primary' onClick={updateMember}>UPDATE</Button> 
            </form>
        </div>
    )
}


const style={
    display: 'flex',
    justifyContent: 'center'
}