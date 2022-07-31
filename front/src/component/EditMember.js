import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import ApiService from '../ApiService';

import {TextField, Button, Typography} from '@material-ui/core'

export default function EditMember() {
    const navigate = useNavigate()
    const [memberId, setMemberId] = useState(window.location.pathname.split('/')[2])
    const [salary, setSalary] = useState('');
    const [message, setMessage] = useState('')

    useEffect(() => loadMember);

    const loadMember = () => {
        ApiService.fetchMemberById(memberId)
            .then(res => {
                let member = res.data.data;
                setMemberId(member.memberId);
                setSalary(member.salary);
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

        let member = {
            id:memberId,
            salary:salary
        }

        ApiService.updateMember(updateMember)
            .then(res => {
                setMessage(member.memberId + '님 정보가 수정되었습니다.')
                console.log(message)
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