import random

from flask import Flask,render_template,request,session,jsonify
from Dbconnection import Db

app = Flask(__name__)
app.secret_key="abc"





@app.route('/adminindex')
def adminindex():
    return render_template('admin/index.html')


@app.route('/sellerindex')
def sellerindex():
    return render_template('seller/seller_index.html')


@app.route('/login_post',methods=['post'])
def login_post():
    c=Db()
    uname=request.form['txt_username']
    password=request.form['txt_password']
    qry="select * from login where username='"+uname+"' and `password`='"+password+"'"
    res=c.selectOne(qry)
    if res is not None:
        type=res['type']
        if type=='admin':
            return adminindex()
        elif type=='seller':
            session['lid']=res['loginid']
            return sellerindex()
        else:
            return '''<script>alert('invalid username or password');window.location='/'</script>'''
    else:
        return '''<script>alert('invalid username or password');window.location='/'</script>'''





@app.route('/admin_home')
def admin_home():
    return render_template("admin/admin_home.html")

@app.route('/admin_add_category')
def admin_add_category():
    return render_template('admin/category.html')

@app.route('/admin_add_category_post',methods=['post'])
def admin_add_category_post():
    c=Db()
    category=request.form['textfield']
    qry="INSERT INTO category (cat_name) VALUES ('"+category+"')"
    r=c.insert(qry)
    return '''<script>alert('Successfully Added');window.location='/admin_add_category'</script>'''


@app.route('/admin_add_view_cat')
def admin_add_view_cat():
    c=Db()
    qry="SELECT * FROM category"
    r=c.select(qry)
    return render_template('admin/view_cat.html',f=r)

@app.route('/admin_add_edit_cat/<cid>')
def admin_add_edit_cat(cid):
    db=Db()
    qry="select * from category where cat_id='"+str(cid)+"'"
    res=db.selectOne(qry)
    session['cid']=cid
    return render_template('admin/edit_cat.html',data=res)

@app.route('/admin_update_cat_post',methods=['post'])
def admin_update_cat_post():
    edit_cat=request.form['textfield']
    db=Db()
    qry="update category set cat_name='"+edit_cat+"' where cat_id='"+str(session['cid'])+"'"
    res=db.update(qry)
    return '''<script>alert('Successfully updated');window.location='/admin_add_view_cat'</script>'''


@app.route('/admin_del_cat/<cid>')
def admin_del_cat(cid):
    db=Db()
    qry="delete from category where cat_id='"+str(cid)+"'"
    res=db.delete(qry)
    return '''<script>alert('Successfully deleted');window.location='/admin_add_view_cat'</script>'''




@app.route('/admin_add_view_post',methods=['post'])
def admin_add_view_post():
    print("qqqqqqqqqqqqqqqqqqqqqq")
    c=Db()
    a=request.form["textfield"]
    print(a)
    qry="select * from category where cat_name like '%"+a+"%'"
    r=c.select(qry)
    print(r)
    return render_template('admin/view_cat.html', f=r)




@app.route('/admin_add_view_more_sellers_approve_or_reject/<sel_id>')
def admin_add_view_more_sellers_approve_or_reject(sel_id):
    c=Db()
    qry="select * from seller where sel_id='"+sel_id+"'"
    r=c.selectOne(qry)
    return render_template('admin/view_more_sellers_aprrove_or_reject.html',r=r)

@app.route('/admin_approve_or_reject_seller_post',methods=['post'])
def admin_add_view_more_sellers_approve_or_reject_post():
    sel_id=request.form["sellerid"]
    but=request.form["button"]
    print(but)
    if but=="accept":
     qry="update  seller set status='accept' where sel_lid='"+sel_id+"'"
     qry1="update login set type='seller' where loginid='"+sel_id+"'"
     print(qry)
     print(qry1)
     c = Db()
     r = c.update(qry)
     w=c.update(qry1)
    else:
     qry="update  seller set status='reject' where sel_lid='"+sel_id+"'"
     qry1="update login set type='reject' where loginid='"+sel_id+"'"
     print(qry)
     print(qry1)
     c = Db()
     r = c.update(qry)
     w=c.update(qry1)
    return admin_add_seller_request()

@app.route('/admin_add_view_approved_seller')
def admin_add_view_approved_seller():
    c=Db()
    qry="select * from seller where status='accept' "
    r=c.select(qry)
    return render_template('admin/view_approved_seller.html',f=r)


@app.route('/admin_approved_or_reject_seller_search_post',methods=['post'])
def admin_add__post():
    c=Db()
    a=request.form["textfield"]
    qry="select * from seller where status='accept' and sel_name like '%"+a+"%'"
    r=c.select(qry)
    return render_template('admin/view_approved_seller.html', f=r)

@app.route('/admin_pending_seller_post_search',methods=['post'])
def admin_pending_seller_post_search():
    c=Db()
    a=request.form["textfield"]
    qry="select * from seller where status='pending' and sel_name like '%"+a+"%'"
    r=c.select(qry)
    return render_template('admin/seller_request.html', r=r)




@app.route('/admin_add_seller_request')
def admin_add_seller_request():
    c=Db()
    qry="select * from seller where status='pending'"
    r=c.select(qry)
    return render_template('admin/seller_request.html',r=r)






@app.route('/admin_add_view_approved_seller_post',methods=['post'])
def admin_add_view_approved_post():
    c=Db()
    a=request.form["textfield"]
    qry="select * from seller where status='accept' and sel_name like '%"+a+"%'"
    r=c.select(qry)
    return render_template('admin/view_approved_seller.html', f=r)



@app.route('/admin_add_view_rejected_seller')
def admin_add_view_rejected_seller():
    c=Db()
    qry="SELECT seller.* FROM seller, login WHERE seller.sel_lid=login.loginid AND login.type='reject' AND seller.status='reject'"
    print(qry)
    r=c.select(qry)
    print(r)
    return render_template('admin/view_rejected_seller.html',f=r)


@app.route('/admin_add_view_rejected_seller_post',methods=['post'])
def admin_add_view_rejected_post():
    c=Db()
    a=request.form["textfield"]
    qry="select * from seller where status='reject' and sel_name like '%"+a+"%'"
    r=c.select(qry)
    return render_template('admin/view_rejected_seller.html', f=r)


@app.route('/admin_add_view_complaint_and_send_replay')
def admin_add_view_complaint_():
    c=Db()
    qry="select customer.name,complaint.* from customer,complaint where complaint.u_id=customer.u_id"
    r = c.select(qry)
    print(r)
    return render_template('admin/view_complaint_and_send_replay.html',data=r)


@app.route('/admin_sending_replay_post/<ab>')
def admin_sending_replay_post(ab):
    c=Db()
    qry="select * from complaint where complaint_id='"+ab+"'"
    r=c.selectOne(qry)
    print(r)
    session['compid']=ab
    return render_template('admin/replay.html')



@app.route('/admin_add_replay_post',methods=['post'])
def admin_add_replay_post():
    c=Db()
    x=session['compid']
    replay=request.form['textfield']
    qry="update complaint set replay= '"+replay+"',status='replied'  where complaint_id='"+str(x)+"'"
    res=c.update(qry)
    return "<script>alert('replied');window.location='/admin_add_view_complaint_and_send_replay'</script>"

# ----------------------------seller--------------------------------------------
@app.route('/seller_registration')
def seller_registration():
    return render_template('seller/seller registation.html')

@app.route('/seller_registration_post',methods=['post'])
def seller_registration_post():
    c=Db()
    print("kkkkkkkkkkkkk")
    name=request.form['name']
    place=request.form['place']
    pin=request.form['pin']
    post=request.form['post']
    phone=request.form['phone']
    image=request.files['file']
    image.save("D:\\Project\\organic_products\\organic_products\\static\\seller_image\\"+image.filename)
    path="/static/seller_image/"+image.filename
    email=request.form['email']
    pasword=request.form['password']
    confirm_pasword=request.form['confirmpassword']
    if pasword==confirm_pasword:
        qry="insert into login(username,password,type) values('"+email+"','"+pasword+"','pending')"
        lid=c.insert(qry)
        qry1="insert into seller(sel_name,sel_place,sel_pin,sel_post,sel_phn,sel_image,sel_email,sel_lid) values('"+name+"','"+place+"','"+pin+"','"+post+"','"+phone+"','"+path+"','"+email+"','"+str(lid)+"')"
        c.insert(qry1)
        return '<script>alert("reqistration successfull");window.location="/"</script>'
    else:
        return '<script>alert("pasword mismatch");window.location="/"</script>'




@app.route('/regindex')
def regindex():
    return render_template('regindex.html')





@app.route('/seller_home')
def seller_home():
    return render_template("seller/seller_home.html")


@app.route('/seller_view_seller_profile')
def seller_view_seller_profile():
    lid=session['lid']
    qry="select * from seller where sel_lid='"+str(lid)+"'"
    db=Db()
    res=db.selectOne(qry)
    return render_template("seller/view seller profile.html",data=res)


@app.route('/seller_product_view')
def seller_product_view():
    c=Db()
    qry="select * from product where seller_lid='"+str(session['lid'])+"'"
    r=c.select(qry)
    return render_template("seller/product management.html",data=r)



@app.route('/seller_product_view_search',methods=['post'])
def seller_product_view_search():
    c=Db()
    name=request.form['nn']
    qry="select * from product where product_name like '%"+name+"%' and seller_lid='"+str(session['lid'])+"' "
    r=c.select(qry)
    return render_template("seller/product management.html",data=r)


@app.route('/seller_add_product')
def seller_add_product():
    qry="select * from category"
    c=Db()
    r=c.select(qry)
    return render_template("seller/add_product.html",r=r)

@app.route('/seller_add_product_post',methods=['post'])
def seller_add_product_post():
    pname=request.form['textfield']
    category=request.form['select']
    quantity=request.form['textfield2']
    price=request.form['textfield3']
    photo=request.files['fileField']
    photo.save("D:\\Project\\organic_products\\organic_products\\static\\product\\"+photo.filename)
    path="static/product/"+photo.filename
    qry="insert into product(product_name,quantity,category,price,photo,seller_lid)values('"+pname+"','"+quantity+"','"+category+"','"+price+"','"+path+"','"+str(session['lid'])+"')"
    c=Db()
    c.insert(qry)
    return '''<script>alert("added successfully");window.location="seller_product_view"</script>'''



@app.route('/seller_edit_product/<pid>')
def seller_edit_product(pid):
    db=Db()
    qry="select * from product where product_id='"+str(pid)+"'"
    res=db.selectOne(qry)
    qry1="select * from category"
    res1=db.select(qry1)
    return render_template("seller/edit_product.html",data=res,data1=res1)

@app.route('/seller_edit_product_post',methods=['post'])
def seller_edit_product_post():
    pname = request.form['textfield']
    category = request.form.get('select')
    quantity = request.form['textfield2']
    price = request.form['textfield3']
    pid=request.form['pid']
    db=Db()
    if 'fileField' in request.files:
        photo = request.files['fileField']
        if photo.filename=='':
            pass
        else:
            photo.save("D:\Project\organic_products\organic_products\static\product" + photo.filename)
            path = "static/product/" + photo.filename
            qry="update  product set product_name ='"+pname+"',quantity='"+quantity+"',category='"+category+"',price='"+price+"',photo='"+path+"' where product_id='"+pid+"'"
            db.update(qry)
    qry="update  product set product_name ='"+pname+"',quantity='"+quantity+"',category='"+category+"',price='"+price+"' where product_id='"+pid+"'"
    db.update(qry)
    return seller_product_view()

@app.route('/seller_delete_product/<pid>')
def seller_delete_product(pid):
    db=Db()
    qry="delete from product where product_id='"+str(pid)+"'"
    db.delete(qry)
    return seller_product_view()




@app.route('/seller_delivary_boy_management_view')
def seller_delivary_boy_management_view():
    c = Db()
    qry = "select * from delivary_boy where seller_lid='"+str(session['lid'])+"'"
    r = c.select(qry)
    return render_template("seller/delivary_boy_management_view.html",data=r)


@app.route('/seller_add_delivary_boy')
def seller_add_delivary_boy():
    return render_template("seller/add_deivary_boy.html")
@app.route('/seller_add_delivary_boy_post',methods=['post'])
def seller_add_delivary_boy_post():
    name=request.form['textfield']
    place=request.form['textfield2']
    pin=request.form['textfield3']
    post=request.form['textfield4']
    contact=request.form['textfield5']
    city=request.form['textfield6']
    email = request.form['textfield7']
    password=random.randint(0000,9999)
    db=Db()
    qry1="insert into login(username, password,type) values('"+email+"','"+str(password)+"','delivery')"
    lid=db.insert(qry1)
    qry="insert into delivary_boy(name,contact_number,place,post,pin,city,email,login_id, seller_lid) values('"+name+"','"+contact+"','"+place+"','"+post+"','"+pin+"','"+city+"','"+email+"','"+str(lid)+"','"+str(session['lid'])+"')"
    db.insert(qry)
    return '''<script>alert("added successfully");window.location="seller_delivary_boy_management_view"</script>'''

@app.route('/seller_delivary_boy_edit_profile/<id>')
def seller_delivary_boy_edit_profile(id):
    qry="select * from delivary_boy WHERE delivery_boy_id='"+str(id)+"'"
    db=Db()
    res=db.selectOne(qry)
    session['idk']=id
    return render_template("seller/delivary_boy_edit_profile.html",data=res)



@app.route('/seller_delivary_boy_update',methods=['post'])
def seller_delivary_boy_update():
    db=Db()
    x=session['idk']
    name = request.form['textfield']
    place = request.form['textfield2']
    pin = request.form['textfield4']
    post = request.form['textfield3']
    contact = request.form['textfield5']
    city = request.form['textfield6']
    qry = "update  delivary_boy  set  name='"+name+"',contact_number='"+contact+"',place='"+place+"',post='"+post+"',pin='"+pin+"',city='"+city+"' where delivery_boy_id='" + str(x) + "'"
    db.update(qry)
    return seller_delivary_boy_management_view()




@app.route('/seller_delivary_boy_delete/<id>')
def seller_delivary_boy_edit(id):
    db = Db()
    qry = "delete from delivary_boy where delivery_boy_id='" + str(id) + "'"
    db.delete(qry)
    return seller_delivary_boy_management_view()


@app.route('/seller_view_order_and_payment_details')
def seller_view_order_and_payment_details():
    c=Db()
    # qry="select order_sub.*,order_main.*,product.product_name,customer.name   from order_main,order_sub,customer,product where customer.u_id=order_main.user_id and order_sub.product_id=product.product_id"
    qry="select order_main.*,customer.name,customer.photo from order_main,customer where customer.u_id=order_main.user_id and order_main.seller_id='"+str(session['lid'])+"' and order_main.status='pending'"
    r=c.select(qry)
    print(r)
    # total=0
    # for nn in r:
    #     total=total+float(nn['amount'])*float(nn['quantity'])
    #     print("-----------",total)
    return render_template("seller/view_order_and_payment_details.html",data=r)


@app.route('/seller_view_order_and_payment_details_more/<nn>')
def seller_view_order_and_payment_details_more(nn):
    c=Db()
    session['orderid']=nn
    qry="select * from delivary_boy  where seller_lid='"+str(session['lid'])+"'"
    res=c.select(qry)
    qry2="select customer.*,order_main.total from customer,order_main where order_main.user_id=customer.u_id and order_main.o_id='"+nn+"'"
    res2=c.selectOne(qry2)
    qry3="select order_sub.s_id,order_sub.o_id,order_sub.product_id,order_sub.quantity as qqty,order_sub.amount,product.* from order_sub,product where product.product_id=order_sub.product_id and order_sub.o_id='"+nn+"'"
    res3=c.select(qry3)
    # total = 0
    # for nn in res3:
    #     total = total + float(nn['amount']) * float(nn['qqty'])
    #     print("-----------", total)
    # qry4="update order_main set total='"+str(totalamou)+"' where o_id='"+nn+"'"
    # res4=c.update(qry4)
    return  render_template("seller/more_option_assign_order.html",data=res,data2=res2,data3=res3)



@app.route('/seller_assigning_order',methods=['post'])
def seller_assigning_post():
    c=Db()
    x=session['orderid']
    did=request.form['select']
    qry="insert into assign(o_id,delivary_boy_id,status)values('"+str(x)+"','"+did+"','pending')"
    res=c.insert(qry)
    qry2="update  order_main  set status='assigned' where o_id='"+str(x)+"'"
    res2=c.update(qry2)
    return seller_view_order_and_payment_details()



@app.route('/')
def login_index():
    return render_template("admin/adm_login.html")






@app.route('/seller_more_option_assign_order')
def seller_more_option_assign_order():
    return render_template("seller/more_option_assign_order.html")



@app.route('/seller_view_delivary_status')
def seller_view_delivary_status():
    c=Db()
    x=session['lid']
    qry="select order_main.*,delivary_boy.*,assign.status as a_status,customer.name as cname,customer.place as cplace ,customer.post as cpost,customer.pin as cpin,customer.email as cmail,customer.conatct as ccon from assign,customer,delivary_boy,order_main where assign.delivary_boy_id=delivary_boy.login_id and assign.o_id=order_main.o_id and order_main.user_id=customer.u_id and order_main.status!='pending' and order_main.seller_id='"+str(x)+"' and `delivary_boy`.`seller_lid`='"+str(x)+"'"
    r=c.select(qry)
    print(qry)
    return render_template("seller/view_delivary_status.html",data=r)





@app.route('/seller_view_delivary_status_search',methods=['post'])
def seller_view_delivary_status_search():
    fdate=request.form['textfield']
    todate=request.form['textfield2']
    c=Db()
    x=session['lid']
    qry="select order_main.*,delivary_boy.*,assign.status as a_status,customer.name as cname,customer.place as cplace ,customer.post as cpost,customer.pin as cpin,customer.email as cmail,customer.conatct as ccon from assign,customer,delivary_boy,order_main where assign.delivary_boy_id=delivary_boy.login_id and assign.o_id=order_main.o_id and order_main.user_id=customer.u_id and order_main.status!='pending' and order_main.seller_id='"+str(x)+"' and order_main.date between '"+fdate+"' and '"+todate+"'"
    r=c.select(qry)
    print(qry)
    print(r)
    return render_template("seller/view_delivary_status.html",data=r)
# @app.route('seller_delivary_status_more')

@app.route('/seller_delivary_status_post',methods=['post'])
def seller_delivary_status_post():
    c=Db()
    x=session['lid']
    request.form['fromdate']
    request.form['todate']
    qry="select product.*,order_main.*,delivary_boy.*,customer.name as cname,customer.place as aplace ,customer.post as cpost,customer.pin as cpin,customer.email as cmail,customer.conatct as ccon from assign,customer,delivary_boy,product,order_main where assign.delivary_boy_id=delivary_boy.delivery_boy_id and assign.o_id=order_main.o_id and order_main.user_id=customer.u_id and assign.status='pending' and order_main.seller_id='"+str(x)+"' and order_main.date between '"+fromdate+"' and '"+todate+"'"
    res=c.select(qry)
    return render_template("seller/more_option_delivary_status.html")



@app.route('/seller_view_rating_and_review')
def seller_view_rating_and_review():
    db=Db()
    qry="select rating_review.*,customer.photo,rating_review.date,customer.name from customer,rating_review where rating_review.userid=customer.u_id"
    res=db.select(qry)
    ar_rt = []

    for im in range(0, len(res)):
        val = str(res[im]["rating"])
        ar_rt.append(val)
    fs = "/static/star/full.jpg"
    hs = "/static/star/half.jpg"
    es = "/static/star/empty.jpg"
    arr = []

    for rt in ar_rt:
        print(rt)
        a = float(rt)

        if a >= 0.0 and a < 0.4:
            print("eeeee")
            ar = [es, es, es, es, es]
            arr.append(ar)

        elif a >= 0.4 and a < 0.8:
            print("heeee")
            ar = [hs, es, es, es, es]
            arr.append(ar)

        elif a >= 0.8 and a < 1.4:
            print("feeee")
            ar = [fs, es, es, es, es]
            arr.append(ar)

        elif a >= 1.4 and a < 1.8:
            print("fheee")
            ar = [fs, hs, es, es, es]
            arr.append(ar)

        elif a >= 1.8 and a < 2.4:
            print("ffeee")
            ar = [fs, fs, es, es, es]
            arr.append(ar)

        elif a >= 2.4 and a < 2.8:
            print("ffhee")
            ar = [fs, fs, hs, es, es]
            arr.append(ar)

        elif a >= 2.8 and a < 3.4:
            print("fffee")
            ar = [fs, fs, fs, es, es]
            arr.append(ar)

        elif a >= 3.4 and a < 3.8:
            print("fffhe")
            ar = [fs, fs, fs, hs, es]
            arr.append(ar)

        elif a >= 3.8 and a < 4.4:
            print("ffffe")
            ar = [fs, fs, fs, fs, es]
            arr.append(ar)

        elif a >= 4.4 and a < 4.8:
            print("ffffh")
            ar = [fs, fs, fs, fs, hs]
            arr.append(ar)

        elif a >= 4.8 and a <= 5.0:
            print("fffff")
            ar = [fs, fs, fs, fs, fs]
            arr.append(ar)

    return render_template("seller/view_rating_and_review.html",data=res,r1=arr,ln=len(arr))




# ------------------------------------------------------------------
@app.route('/android_cust_signup', methods=['POST'])
def amdroid_cust_signup():
    c = Db()
    name = request.form['name']
    place = request.form['place']
    post = request.form['post']
    pin = request.form['pin']
    email = request.form['email']
    phone = request.form['phonenumber']
    import base64
    password = request.form['password']
    img = request.form["photo"]

    qry = "insert into login(username,password,type) values('" + email + "','" + password + "','customer')"
    res = c.insert(qry)
    u_id = str(res)
    a = base64.b64decode(img)
    fh = open("D:\\Project\\organic_products\\organic_products\\static\\customer\\" + str(u_id) + ".jpg", "wb")
    fh.write(a)
    fh.close()
    path = "/static/customer/" + str(u_id) + ".jpg"
    qry2 = "insert into customer(name,place,post,pin,email,conatct,u_id,photo) values('" + name + "','" + place + "','" + post + "','" + pin + "','" + email + "','" + phone + "','" + u_id + "','" + path + "')"
    res2 = c.insert(qry2)
    return jsonify(status="ok")


@app.route('/and_login', methods=['POST'])
def android_login():
    c = Db()
    uname = request.form['username']
    password = request.form['password']
    qry = "select * from login where username='" + uname + "' and `password`='" + password + "'"
    print(qry)
    res = c.selectOne(qry)
    if res is not None:
        if res['type'] == 'delivery':
            return jsonify(status='ok', type=res['type'], lid=res['loginid'])
        if res['type'] == 'customer':
            return jsonify(status='ok', type=res['type'], lid=res['loginid'])
        else:
            return jsonify(status="no")
    else:
        return jsonify(status="no")

@app.route("/and_cust_view_nearby_seller_no", methods=['POST'])
def and_cust_view_nearby_seller_no():
    c = Db()
    # lati=request.form["latitude"]
    # longi=request.form["longitude"]
    # kw = request.form["kw"]

    qry = "select * from seller"

    print(qry)
    res = c.select(qry)
    if res is not None:
        return jsonify(status='ok', res=res)
    else:
        return jsonify(status="no")
@app.route("/and_cust_view_nearby_seller", methods=['POST'])
def and_cust_view_nearby_seller():
    c = Db()
    # lati=request.form["latitude"]
    # longi=request.form["longitude"]
    kw = request.form["kw"]
    if kw == "":
        qry = "select * from seller"
    else:
        qry = "select * from seller where sel_name like '%" + kw + "%'"
    print(qry)
    res = c.select(qry)
    if res is not None:
        return jsonify(status='ok', res=res)
    else:
        return jsonify(status="no")


@app.route("/and_view_product_each_seller", methods=['POST'])
def and_view_product_each_seller():
    c = Db()
    selid = request.form["lid"]
    qry = "select * from product where seller_lid='" + selid + "'"
    print(qry)
    res = c.select(qry)
    if res is not None:
        return jsonify(status='ok', res=res)
    else:
        return jsonify(status="no")


@app.route("/and_add_to_cart", methods=['POST'])
def and_add_to_cart():
    c = Db()
    product_name = request.form['pid']
    user_id = request.form['user_id']
    qty = request.form["qty"]
    qry = "insert into cart(product_id,user_id,qty)values('" + product_name + "','" + user_id + "','" + qty + "')"
    res = c.insert(qry)
    if res is not None:
        return jsonify(status='ok', data=res)
    else:
        return jsonify(status="no")


@app.route("/and_delete_cart", methods=['POST'])
def and_delete_cart():
    c = Db()
    cart_id = request.form['cid']
    qry = "delete from cart where cart_id='" + cart_id + "'"
    res = c.insert(qry)
    if res is not None:
        return jsonify(status='ok', data=res)
    else:
        return jsonify(status="no")


@app.route("/userviewcart", methods=['POST'])
def and_view_product_cart():
    c = Db()
    selid = request.form["lid"]
    dist = request.form["selid"]
    qry = "select cart.cart_id,cart.qty,product.product_name,product.price,product.photo from product inner join cart on cart.product_id=product.product_id where cart.user_id='" + selid + "' and product.seller_lid='" + dist + "'"
    res = c.select(qry)
    if res is not None:
        return jsonify(status='ok', data=res)
    else:
        return jsonify(status="no")


@app.route("/and_add_to_cart_and_payment", methods=['POST'])
def and_add_to_cart_and_payment():
    c = Db()
    selid = request.form["lid"]
    dist = request.form["selid"]
    address = request.form["address"]
    qry = "select cart.cart_id,cart.qty,product.product_name,product.price,product.photo,product.product_id from product inner join cart on cart.product_id=product.product_id where cart.user_id='" + selid + "' and product.seller_lid='" + dist + "'"
    print(qry)
    res = c.select(qry)
    print(res)
    if res is not None:
        qry1 = "insert into order_main(user_id,date,total,status,seller_id,delivary_address) values('" + selid + "',curdate(),'0','pending','" + dist + "','" + address + "')"
        oid = c.insert(qry1)
        tot = 0
        for i in res:
            product_id = str(i["product_id"])
            quantity = int(i['qty'])
            price = int(i["price"])
            amount = quantity * price
            tot = tot + amount

            qry1 = "insert into order_sub(o_id,product_id,quantity,amount)values('" + str(
                oid) + "','" + product_id + "','" + str(quantity) + "','" + str(amount) + "')"
            res = c.insert(qry1)
            qry5 = "delete from cart where cart_id='" + str(i["cart_id"]) + "'"
            dd = c.delete(qry5)
        qry4 = "update order_main set total='" + str(tot) + "' where o_id='" + str(oid) + "'"
        c.update(qry4)
        return jsonify(status='ok', tot=str(tot), oid=str(oid))
    else:
        return jsonify(status="no")


@app.route("/insertorder", methods=['POST'])
def insertorder():
    oid = request.form["oid"]
    total = int(request.form["tot"])
    bank = request.form["bank"]
    acc = request.form["acc"]
    pin = request.form["pin"]
    c = Db()
    s = "select balance from bank where bank_name='" + bank + "' and account='" + acc + "' and pin='" + pin + "'"
    res = c.selectOne(s)
    if res is not None:
        if int(res["balance"]) > total:
            od = "update bank set balance=balance-'" + str(
                total) + "' where bank_name='" + bank + "' and account='" + acc + "' and pin='" + pin + "'"
            c.update(od)

            dd = "insert into payment(order_id,account_no,bank_name,amount,date) values('" + str(oid) + "','" + str(
                acc) + "','" + str(bank) + "','" + str(total) + "',curdate())"
            c.insert(dd)
            return jsonify(status="ok")
        else:
            return jsonify(status="no")
    else:
        return jsonify(status="no")


@app.route("/viewmyorders", methods=['POST'])
def mym():
    uid = request.form["lid"]
    d = "select order_main.total,order_main.status,seller.sel_name,seller.sel_image,order_main.o_id from seller inner join order_main on order_main.seller_id=seller.sel_lid where order_main.user_id='" + uid + "'"
    c = Db()
    print(d)
    res = c.select(d)
    print(res)
    if len(res) > 0:
        return jsonify(status="ok", data=res)
    else:
        return jsonify(status="no")


@app.route("/and_view_orderitems", methods=['POST'])
def and_view_product_odrd():
    c = Db()
    oid = request.form["oid"]
    qry = "select product.product_name,product.price,product.photo,order_sub.quantity,product.product_id from order_sub inner join product on order_sub.product_id=product.product_id where order_sub.o_id='" + oid + "'"
    res = c.select(qry)
    if res is not None:
        return jsonify(status='ok', data=res)
    else:
        return jsonify(status="no")


@app.route("/and_add_rating", methods=['POST'])
def and_add_review():
    reviw = request.form["review"]
    rating = request.form["rating"]
    userid = request.form["lid"]
    qry = "insert into rating_review(review,userid,rating,date) values('" + reviw + "','" + userid + "','" + rating + "',curdate())"
    c = Db()
    res = c.insert(qry)
    if res is not None:
        return jsonify(status='ok', data=res)
    else:
        return jsonify(status="no")


@app.route("/deliver_view_profile", methods=['POST'])
def deliver_view_profile():
    c = Db()
    log = request.form["lid"]
    qry = "select delivary_boy.name,delivary_boy.contact_number,delivary_boy.place,delivary_boy.post,delivary_boy.pin,delivary_boy.city,delivary_boy.email,seller.sel_name from delivary_boy inner join seller on seller.sel_lid=delivary_boy.seller_lid where delivary_boy.login_id='" + log + "'"
    print(qry)
    res = c.selectOne(qry)
    print(res)
    if res is not None:
        return jsonify(status='ok', name=res["name"], phone=res["contact_number"], place=res["place"], post=res["post"],
                       pin=res["pin"], city=res["city"], email=res['email'], sel_name=res['sel_name'])
    else:
        return jsonify(status="no")

@app.route("/customer_view_profile", methods=['POST'])
def customer_view_profile():
    c = Db()
    log = request.form["lid"]
    qry = "SELECT customer.name, customer.place,customer.post,customer.pin,customer.email,customer.conatct,customer.u_id,customer.photo from customer where customer.u_id='" + log + "'"
    print(qry)
    res = c.selectOne(qry)
    print(res)
    if res is not None:
        return jsonify(status='ok', name=res["name"], phone=res["conatct"], place=res["place"], post=res["post"],
                       pin=res["pin"], email=res['email'],photo=res["photo"])
    else:
        return jsonify(status="no")

@app.route("/delivery_view_orders", methods=['POST'])
def deliver_view_orders():
    c = Db()
    lid = request.form["parcelid"]
    qry = "select order_main.o_id,customer.name,order_main.total,order_main.status,order_main.delivary_address,order_main.date,customer.conatct from order_main inner join customer on customer.u_id=order_main.user_id inner join assign on assign.o_id=order_main.o_id where assign.o_id='" + lid + "'"
    print(qry)
    res = c.selectOne(qry)
    if res is not None:
        return jsonify(status='ok', name=res["name"], conatct=res["conatct"], delivary_address=res["delivary_address"],
                       total=res["total"], status1=res["status"], date=res["date"], o_id=res['o_id'])
    else:
        return jsonify(status="no")


@app.route('/agent_view_assigned_parcels', methods=["post"])
def agent_view_assigned_parcels():
    dbid = request.form["lid"]
    sel = "select order_main.o_id,order_main.delivary_address,order_main.date,order_main.user_id,customer.name  from order_main inner join assign on assign.o_id=order_main.o_id  inner join customer on customer.u_id=order_main.user_id where  assign.delivary_boy_id='" + dbid + "' and assign.status!='Order Delivered'"
    print(sel)
    c = Db()
    n = c.select(sel)
    print(n)
    return jsonify(status="ok", data=n)


@app.route("/change_password", methods=['POST'])
def change_password():
    email = request.form["email"]
    passwd = request.form["old"]
    lid = request.form["lid"]
    newpa = request.form["new"]
    s = "select * from login where username='" + email + "' and password='" + passwd + "' and loginid='" + lid + "'"
    c = Db()
    res = c.select(s)
    if res is not None:
        ss = "update login set password='" + newpa + "' where loginid='" + lid + "'"
        print(ss)
        c.update(ss)
        return jsonify(status="ok")
    else:
        return jsonify(status="no")


@app.route("/deliver_parcel", methods=['POST'])
def deliver_parcel():
    o_id = request.form["parcelid"]
    status = request.form["status"]
    s = "update order_main set status='" + status + "' where o_id='" + o_id + "'"
    s1 = "update assign set status='" + status + "' where o_id='" + o_id + "'"
    c = Db()
    c.update(s)
    c.update(s1)
    return jsonify(status="ok")


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')








