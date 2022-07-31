import React, { useState } from 'react'
import ApiService from '../ApiService'

import {TextField, Button, Typography} from '@material-ui/core'
import { useNavigate } from 'react-router-dom'

export default function AddMember() {

    const navigate = useNavigate()
    const [memberId, setMemberId] = useState('')
    const [memberName, setmemberName] = useState('')
    const [password, setpassword] = useState('')
    const [age, setAge] = useState('')
    const [salary, setSalary] = useState('')
    const [message, setMessage] = useState('')

    const onChangeMemberId = (e) => {
        setMemberId(e.target.value);
    }

    const onChangeMemberName = (e) => {
        setmemberName(e.target.value);
    }

    const onChangePassword = (e) => {
        setpassword(e.target.value);
    }

    const onChangeAge = (e) => {
        setAge(e.target.value);
    }

    const onChangeSalary = (e) => {
        setSalary(e.target.value);
    }

    const saveMember = (e) => {
        e.preventDefault();

        let member = {
            id:memberId,
            name: memberName,
            password:password,
            age:age
        }
        ApiService.addMember(member)
            .then(res => {
                if(res.data.message === 'success'){
                    setMessage(member.name + '님이 등록되었습니다')
                    console.log(message);
                }
            })
            .catch(error => {
                console.log('addMember() Error', error);
            })
        navigate('/')
    }

    return (
        <div>
            <Typography variant='h4' style={style}>Add Member</Typography>
            <form style={form}>
                <TextField type="text" placeholder='input Id' name='memberId'
                    fullWidth value={memberId} onChange={onChangeMemberId}/>
                <TextField type="text" placeholder='input name' name='memberName'
                    fullWidth value={memberName} onChange={onChangeMemberName}/>
                <TextField type="password" placeholder='input password' name='password'
                    fullWidth value={password} onChange={onChangePassword}/>
                <TextField type="number" placeholder='input age' name='age'
                    fullWidth value={age} onChange={onChangeAge}/>
                <TextField type="number" placeholder='input salary' name='salary'
                    fullWidth value={salary} onChange={onChangeSalary}/>

                <Button variant='contained' color='primary' onClick={saveMember}>SAVE</Button> 
            </form>
        </div>
    )
}

const form={
    display: 'flex',
    flexFlow: 'row wrap'
}

const style = {
    display: 'flex',
    justifyContent: 'center'
}