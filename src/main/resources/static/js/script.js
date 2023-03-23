function togglesidebar(){
  var x = document.getElementById("dis");
  var x1 = document.getElementById("dis1");
  if (x.style.display === "none") {
    x.style.display = "block";
    x1.style.marginLeft="20%";

  } else {
    x.style.display = "none";
        x1.style.marginLeft="2%";
  }
}