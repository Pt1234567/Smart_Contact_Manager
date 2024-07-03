let currentTheme=getTheme();
console.log(currentTheme);
changeTheme(currentTheme);

function changeTheme(){
  document.querySelector('html').classList.add(currentTheme);

  

  //set button change
   const changeThemeButton=document.querySelector('#theme_change_button');
   
   changeThemeButton.addEventListener("click",(event)=>{
      const oldTheme=currentTheme;
      if(currentTheme === "dark"){
            //theme to light
            currentTheme="light";
      }else{
            currentTheme="dark";
      }
      //local storage mai update karenge
      changeThemeButton.querySelector('span').textContent= currentTheme==="dark"?"Light":"Dark";
      setTheme(currentTheme);
      //remove curr theme
      if(oldTheme!=null)document.querySelector('html').classList.remove(oldTheme);
      //set current theme
      document.querySelector('html').classList.add(currentTheme);
      
      ///console.log(currentTheme);
      //change text
      
   });
   
   
   
   
}

//set theme to local storage
function setTheme(theme){
 localStorage.setItem("theme",theme);
}

//get theme from local storage
function getTheme(){
   let theme= localStorage.getItem("theme");
   if(theme){
      return theme;
   }else return "light";
}