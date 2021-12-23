use Java6Lab8
CREATE PROC [dbo].[sp_getTotalPriceOneMonth]
(
	@month VARCHAR(2),
	@year VARCHAR(4)
)
AS BEGIN
	DECLARE @result VARCHAR(20)
	SET @result = (SELECT SUM(o2.price) AS 'totalprice'
					FROM orders o1 INNER JOIN orderDetails o2 ON o1.id = o2.orderId
					WHERE MONTH(o1.CreateDate) = @month AND YEAR(o1.CreateDate) = @year)
	IF @result IS NULL BEGIN SET @result = '0' END
	SELECT @result
END

EXEC sp_getTotalPriceOneMonth '07', '1977' 