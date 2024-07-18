console.log("contact");

const viewContactModel=document.getElementById('view_contact_modal');

const options = {
      placement: 'bottom-right',
      backdrop: 'dynamic',
      backdropClasses:
          'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
      closable: true,
      onHide: () => {
          console.log('modal is hidden');
      },
      onShow: () => {
          console.log('modal is shown');
      },
      onToggle: () => {
          console.log('modal has been toggled');
      },
  };

  // instance options object
const instanceOptions = {
      id: 'view_contact_modal',
      override: true
    };

    const contactModal=new Modal(viewContactModel,options,instanceOptions);


    function openContactModal(){
         contactModal.show();
    }
     
    function closeContactModal(){
      contactModal.hide();
    }

    async function loadContactData(id){
                

                try {
                  console.log(id);
                const data= await (await fetch(`http://localhost:8080/api/contacts/${id}`)).json();
                document.querySelector("#contact_name").innerHTML=data.contactName;
                document.querySelector("#contact_email").innerHTML=data.contactEmail;
                document.querySelector("#contact_phone").innerHTML=data.contactPhone;
                document.querySelector("#contact_image").src=data.contactPicture;
                console.log("picture :"+data.contactPicture);
                openContactModal();
                } catch (error) {
                  console.log(error);
                }
    }

     async function deleteContact(id){
     
      Swal.fire({
        title: "Do you want to delete the contact?",
        icon:"warning",
        showCancelButton: true,
        confirmButtonText: "Save",
      }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
          const url="http://localhost:8080/user/contacts/delete/"+id;
          window.location.replace(url);
        }
      });  

    }