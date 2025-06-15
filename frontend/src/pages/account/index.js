import { useEffect, useState } from "react";
import axios from "axios";
import {jwtDecode} from "jwt-decode";
import {useRouter} from "next/router"


export default function UserDetails() {


    const router=useRouter()
    const [user, setUser] = useState(null);
    const [error, setError] = useState("");
    const [userName, setUsernameChange] = useState("");




    async function changeUsername(e){
        e.preventDefault();

        router.replace('/account');
        console.log("hi i am changeUsername function");

        const token =localStorage.getItem("token");
        if (!token){
            setError("NO token - kane login ");
            window.location.href = "/login";
        }
        const {id}=jwtDecode(token);
        const {username}=jwtDecode(token)



            console.log(id);
            console.log("ela re ton arxonta",username);
            console.log("etsi pairnw ta stoixeia apo ton uparxonta user",user.id);
         user.name = usernameChange.value;
         console.log("ta exw gamisei ola",user.name);

        try {
            const response = await axios.put(
                "http://localhost:8080/users",
                user,
                {
                    params: { id },
                    headers: {
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json"
                    }
                }
            );

            console.log("asd",user)
            console.log("Updated user:", response.data);
            console.log("Updated user:", usernameChange.value);



        }
        catch (err){
            console.log(err)
            setError("apotuxia Update");
        }

        setUsernameChange('');

    }


   async function deleteUser(e){
       e.preventDefault();
       const token =localStorage.getItem("token");
       if (!token){
           setError("NO token - kane login ");
           window.location.href = "/login";
           return;
       }

       const {id}=jwtDecode(token);

       try{
           await axios.delete(`http://localhost:8080/users/${id}`, {
           headers: {
               Authorization: `Bearer ${token}`
           }
       });

           localStorage.removeItem("token");
           window.location.href = "/login";


       }catch(err){
           setError("Delete failed: ELA KAI POU EISAI",id);
           console.error(err);
       }


    }

    useEffect(() => {

        const token = localStorage.getItem("token");
        if (!token){
            setError("NO token - kane login ");
            window.location.href = "/login";
            return;
        }
        //console.log("token",token);
        const {id}=jwtDecode(token);


        console.log("id is : ",id);
        axios.get(`http://localhost:8080/users/${id}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).then((res) => {

                setUser(res.data);
            })
            .catch((err) => {
                setError("Failed to fetch user details re malaka");
                console.error(err);
                setTimeout(function() {
                    window.location.href = "/login";
                }, 3000);

            });
    }, []);

    function handleUsernameChange(event){
        setUsernameChange(event.target.value);
    }

    if (error) return <p>{error}</p>;
    if (!user) return <p>Loading user details...</p>;




    return (
        <>
            <div>
                <h2>User Details</h2>
                <p><strong>ID:</strong> {user.id}</p>
                <p><strong>Name:</strong> {user.name}</p>
                <p><strong>Email:</strong> {user.email}</p>
            </div>



            <div>
                <form onSubmit={changeUsername}>
                <label >Change Username:</label>
                <input type="text"
                       id="usernameChange"
                       value={userName}
                       onChange={handleUsernameChange}


                       placeholder="Enter your new UserName"
                       />
                <button type="submit"> Change </button>
                </form>
            </div>



            <div>
                <form onSubmit={deleteUser}>
                    <button type="submit">Delete account</button>
                </form>
            </div>


        </>
    );
}
